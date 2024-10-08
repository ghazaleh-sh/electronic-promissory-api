package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.RequestType;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.entities.Promissory;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.entities.PromissoryStakeholder;
import ir.co.sadad.promissory.mapper.PromissoryMapper;
import ir.co.sadad.promissory.providers.promissory.PromissoryClient;
import ir.co.sadad.promissory.providers.sso.services.SSOTokenService;
import ir.co.sadad.promissory.services.daos.PromissoryDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryLogDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryRequestDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryStakeholderDaoService;
import ir.co.sadad.promissory.services.utils.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static ir.co.sadad.promissory.commons.Constants.TERMINAL_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromissoryServiceImpl implements PromissoryService {

    private final PromissoryClient promissoryClient;
    private final SSOTokenService promissoryTokenService;
    private final CertificationService certificationService;
    private final PromissoryBaseService promissoryBaseService;

    private final PromissoryDaoService promissoryDaoService;
    private final PromissoryRequestDaoService requestDaoService;
    private final PromissoryStakeholderDaoService stakeholderDaoService;

    private final PromissoryMapper mapper;
    private final PromissoryLogDaoService logDaoService;

    @Override
    @SneakyThrows
    public FeeInquiryResDto feeInquiry(String authToken, Long promissoryAmount) {
        try {
            return promissoryClient.feeInquiry(promissoryTokenService.getToken(),
                            TERMINAL_ID,
                            FeeInquiryReqDto.builder().amount(promissoryAmount).build())
                    .getInfo();

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    null, null, "feeInquiry", "EXCEPTION");
            throw e;
        }
    }

    @Override
    public IssueAndGuaranteeRegisterResDto issueRegister(String authToken, String issuerSSN, IssueFirstReqDto issueReqDto) {
        issueReqDto.setIssuerNN(issuerSSN);
        PromissoryClientResponseDto<IssueRegisterResDto> issueRes = null;
        String requestId = null;
        try {
            issueRes = promissoryClient.publicRegister(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    mapper.toIssueRegisterReqDto(issueReqDto));

            requestId = issueRes.getInfo().getRequestId();
            logDaoService.saveLogs("", "",
                    requestId, DataConverter.convertResponseToJson(issueRes), "issueRegister", "SUCCESSFUL");


            IssueAndGuaranteeRegisterResDto res = certificationService.certificationFlowToRegister(authToken, requestId, issueRes.getInfo().getUnSignedPdf());

            Promissory savedPromissory = promissoryDaoService.createPromissory(issueReqDto, issueRes.getInfo());
            stakeholderDaoService.createStakeholdersForIssue(issueReqDto,
                    requestDaoService.createPromissoryRequest(issueRes.getInfo().getRequestId(),
                            savedPromissory, RequestType.ISSUE, RequestStatus.REGISTERED));

            return res;

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    requestId,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "issueRegister", "EXCEPTION");

            if (issueRes != null && issueRes.getInfo() != null)
                promissoryBaseService.cancelRequest(issueRes.getInfo().getRequestId());

            throw e;
        }
    }

    @SneakyThrows
    @Override
    public IssueSecondResDto issueApprove(String authToken, IssueSecondReqDto secondReqDto, String otp) {
        try {
            PromissoryRequest savedRequest = requestDaoService.getRequestBy(secondReqDto.getRequestId());

            PromissoryClientResponseDto<IssueApproveResDto> issueApproveRes = promissoryClient.publicApprove(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    IssueApproveReqDto.builder()
                            .requestId(secondReqDto.getRequestId())
                            .signedPdf(secondReqDto.getSignatureValue())
                            .build());

            logDaoService.saveLogs("", "",
                    secondReqDto.getRequestId(), DataConverter.convertResponseToJson(issueApproveRes), "issueApprove", "SUCCESSFUL");

            /*------DB actions-------*/
            Promissory updatedPromissory = promissoryDaoService.updatePromissoryIssue(issueApproveRes.getInfo(), savedRequest.getPromissory().getPromissoryUid());
            PromissoryStakeholder issuer = stakeholderDaoService.getStakeholderByRole(savedRequest, StakeholderRole.ISSUER);
            PromissoryStakeholder recipient = stakeholderDaoService.getStakeholderByRole(savedRequest, StakeholderRole.CURRENT_OWNER);
            requestDaoService.updatePromissoryRequestStatus(savedRequest, RequestStatus.PRIMITIVE);

            IssueSecondResDto response = mapper.toIssueApproveDto(updatedPromissory);
            response.setIssuerNN(issuer.getNationalNumber());
            response.setIssuerCellphone(issuer.getCellphone());
            response.setIssuerFullName(issuer.getFullName());
            response.setIssuerAccountNumber(issuer.getAccountNumber());
            response.setIssuerAddress(issuer.getAddress());
            response.setIssuerPostalCode(issuer.getPostalCode());
            response.setIssuerBirthdate(issuer.getBirthdate());
            response.setRecipientNN(recipient.getNationalNumber());
            response.setRecipientCellphone(recipient.getCellphone());
            response.setRecipientFullName(recipient.getFullName());
            response.setPaymentPlace(recipient.getPaymentPlace());
            return response;
        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    secondReqDto.getRequestId(),
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "issueApprove", "EXCEPTION");

            promissoryBaseService.cancelRequest(secondReqDto.getRequestId());
            throw e;
        }
    }

}
