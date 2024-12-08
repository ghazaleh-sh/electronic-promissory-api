package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.IssueAndGuaranteeAndSettlementRegisterResDto;
import ir.co.sadad.promissory.dtos.UserCertificationResDto;
import ir.co.sadad.promissory.dtos.baambase.CertificateEncodedResDto;
import ir.co.sadad.promissory.dtos.icms.LegalCustomerInfoDto;

public interface CertificationService {
    /**
     * to check user active certification
     *
     * @param authToken user's token (password type)
     * @return UserCertificationResponseDto
     */
    UserCertificationResDto userCertifications(String authToken, String requestId);

    /**
     * to get encoded user certification
     *
     * @param authToken          user's token (password type)
     * @param certificationKeyId user certificationKeyId taken from {@userCertifications} service
     * @return CertificateEncodedResDto
     */
    CertificateEncodedResDto encodedCertification(String authToken, String certificationKeyId, String requestId);

    /**
     * to get dataToBeSign value in order to send to frontEnd, then frontEnd sign this data through IranSign
     *
     * @param authToken
     * @param encodedCert is certificatePemFile got through encodedCertification service
     * @param unsignedPdf is unSignedPdf got through issueRegister service
     * @return
     */
    String generateToBeSign(String authToken, String encodedCert, String unsignedPdf, String requestId);

    /**
     * full flow for generating dataToBeSign
     * Firstly gets userCertification, then gets encoded data of user, and lastly generates dataToBeSign
     *
     * @param authToken
     * @param requestId
     * @param unSignedPdf
     * @return
     */
    IssueAndGuaranteeAndSettlementRegisterResDto certificationFlowToRegister(String authToken, String requestId, String unSignedPdf);

    /**
     * cif service to get legal customer information
     * @param authToken
     * @param identifier id of user
     * @return
     */
    LegalCustomerInfoDto getLegalCustomerInfo(String authToken, String identifier);
}
