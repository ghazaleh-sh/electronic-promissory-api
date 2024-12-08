package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.IssueAndGuaranteeAndSettlementRegisterResDto;
import ir.co.sadad.promissory.dtos.UserCertificationResDto;
import ir.co.sadad.promissory.dtos.baambase.BaamBaseResponseDto;
import ir.co.sadad.promissory.dtos.baambase.CertificateEncodedResDto;
import ir.co.sadad.promissory.dtos.baambase.CertificationResBodyDto;
import ir.co.sadad.promissory.dtos.baambase.DataToBeSignReqDto;
import ir.co.sadad.promissory.dtos.icms.LegalCustomerInfoDto;
import ir.co.sadad.promissory.mapper.PromissoryMapper;
import ir.co.sadad.promissory.providers.baambase.BaambaseClient;
import ir.co.sadad.promissory.providers.icms.ICMSClient;
import ir.co.sadad.promissory.services.daos.PromissoryLogDaoService;
import ir.co.sadad.promissory.services.utils.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static ir.co.sadad.promissory.commons.Constants.SIGNATURE_ALGORITHM;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final BaambaseClient baambaseClient;
    private final ICMSClient icmsClient;
    private final PromissoryLogDaoService logDaoService;

    private final PromissoryMapper mapper;

    @Value(value = "${client.baambase.namad-product-uid}")
    protected Integer namadProductId;


    @SneakyThrows
    @Override
    //TODO: should be cached
    public UserCertificationResDto userCertifications(String authToken, String requestId) {
        BaamBaseResponseDto<List<CertificationResBodyDto>> response = null;
        try {
            response = baambaseClient.userCertification(authToken);

            logDaoService.saveLogs("", "",
                    requestId, DataConverter.convertResponseToJson(response), "userCertifications", "SUCCESSFUL");

        } catch (Exception e) {
            if (requestId == null) // means this method is called directly, not by issueRegister service
                logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                        null, null, "userCertifications", "EXCEPTION");
            throw e;
        }

        List<CertificationResBodyDto> filteredCert = response.getResponseBody().stream()
                .filter(deviceCertification -> deviceCertification.getProductUid().equals(namadProductId))
                .filter(namadCertification -> namadCertification.getStatus().equals("OK")).toList();

        if (filteredCert.isEmpty())
            throw new PromissoryException("CERTIFICATE_NOT_FOUND", HttpStatus.BAD_REQUEST, "1001");

        return mapper.toUserCertificationResDto(filteredCert.getLast());

    }

    @SneakyThrows
    @Override
    public CertificateEncodedResDto encodedCertification(String authToken, String certificationKeyId, String requestId) {
        BaamBaseResponseDto<CertificateEncodedResDto> encodedCert = baambaseClient.getEncodedCertification(authToken, certificationKeyId);

        logDaoService.saveLogs("", "",
                requestId, DataConverter.convertResponseToJson(encodedCert), "encodedCertification", "SUCCESSFUL");

        return encodedCert.getResponseBody();

    }

    @SneakyThrows
    @Override
    public String generateToBeSign(String authToken, String encodedCert, String unsignedPdf, String requestId) {
        DataToBeSignReqDto signReqDto = new DataToBeSignReqDto();
        signReqDto.setDocument(unsignedPdf);
        signReqDto.setParameters(DataToBeSignReqDto.Parameters.builder()
                .signingCertificate(DataToBeSignReqDto.Parameters.SigningCertificateObject.builder()
                        .encodedCertificate(encodedCert).build())
                .signatureAlgorithm(SIGNATURE_ALGORITHM)
                .build());

        String dataToBeSignRes = baambaseClient.dataToBeSignGeneration(authToken, signReqDto)
                .getResponseBody()
                .getBytes();

        logDaoService.saveLogs("", "",
                requestId, "--dataToBeSign--", "generateToBeSign", "SUCCESSFUL");

        return dataToBeSignRes;

    }

    public IssueAndGuaranteeAndSettlementRegisterResDto certificationFlowToRegister(String authToken, String requestId, String unSignedPdf) {

        UserCertificationResDto userCertification = userCertifications(authToken, requestId);

        String encodedCert = encodedCertification(authToken, userCertification.getCertificateKeyId(), requestId)
                .getCertificatePemFile();

        String dataToBeSign = generateToBeSign(authToken, encodedCert, unSignedPdf, requestId);

        return getIssueAndGuaranteeRegisterResDto(requestId, userCertification, dataToBeSign);
    }

    @Override
    public LegalCustomerInfoDto getLegalCustomerInfo(String authToken, String identifier) {
        LegalCustomerInfoDto res = icmsClient.getLegalCustomerInfo(authToken, identifier);

        logDaoService.saveLogs("", "",
                null, "--legalCustomerInfo--", "getLegalCustomerInfo", "SUCCESSFUL");

        return res;
    }

    private static IssueAndGuaranteeAndSettlementRegisterResDto getIssueAndGuaranteeRegisterResDto(String requestId, UserCertificationResDto userCertification, String dataToBeSign) {
        IssueAndGuaranteeAndSettlementRegisterResDto response = new IssueAndGuaranteeAndSettlementRegisterResDto();
        response.setRequestId(requestId);
        response.setCertificateKeyId(userCertification.getCertificateKeyId());
        response.setDevice(userCertification.getDevice());
        response.setDeviceName(userCertification.getDeviceName());
        response.setDeviceId(userCertification.getDeviceId());
        response.setProductType(userCertification.getProductType());
        response.setProductId(userCertification.getProductId());
        response.setDataToBeSign(dataToBeSign);
        return response;
    }
}
