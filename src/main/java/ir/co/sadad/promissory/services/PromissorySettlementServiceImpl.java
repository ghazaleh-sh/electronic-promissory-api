package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.RequestType;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.IssueAndGuaranteeAndSettlementRegisterResDto;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.entities.Promissory;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.mapper.PromissoryMapper;
import ir.co.sadad.promissory.providers.promissory.PromissoryClient;
import ir.co.sadad.promissory.providers.sso.services.SSOTokenService;
import ir.co.sadad.promissory.services.daos.PromissoryDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryLogDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryRequestDaoService;
import ir.co.sadad.promissory.services.utils.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static ir.co.sadad.promissory.commons.Constants.TERMINAL_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromissorySettlementServiceImpl implements PromissorySettlementService {

    private final PromissoryClient promissoryClient;
    private final SSOTokenService promissoryTokenService;
    private final CertificationService certificationService;
    private final PromissoryBaseService promissoryBaseService;

    private final PromissoryMapper mapper;
    private final PromissoryLogDaoService logDaoService;
    private final PromissoryDaoService promissoryDaoService;
    private final PromissoryRequestDaoService requestDaoService;

    @Override
    public IssueAndGuaranteeAndSettlementRegisterResDto gradualSettlementRegister(String authToken, String ssn, SettlementRegisterReqDto settlementRegisterReqDto) {
        settlementRegisterReqDto.setOwnerNN(ssn);
        PromissoryClientResponseDto<GuaranteeAndSettlementRegisterResDto> settlementRes = null;
        String requestId = null;
        try {
            settlementRes = promissoryClient.gradualSettlementRegister(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    mapper.toRegisterSettlementReqDto(settlementRegisterReqDto));

            requestId = settlementRes.getInfo().getRequestId();
            logDaoService.saveLogs("", "",
                    requestId, DataConverter.convertResponseToJson(settlementRes), "gradualSettlementRegister", "SUCCESSFUL");


            IssueAndGuaranteeAndSettlementRegisterResDto res = certificationService.certificationFlowToRegister(authToken, requestId, settlementRes.getInfo().getUnSignedPdf());

            //TODO: where should I save amount in DB?
            Promissory savedPromissory = promissoryDaoService.getPromissoryBy(settlementRegisterReqDto.getPromissoryId());
            requestDaoService.createPromissoryRequest(settlementRes.getInfo().getRequestId(),
                    savedPromissory, RequestType.GRADUAL_SETTLEMENT, RequestStatus.GRADUAL_SETTLEMENT_REGISTERED);

            return res;

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    requestId,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "gradualSettlementRegister", "EXCEPTION");

            if (settlementRes != null && settlementRes.getInfo() != null)
                promissoryBaseService.cancelRequest(settlementRes.getInfo().getRequestId());

            throw e;
        }
    }

    @Override
    public SettlementApproveResDto gradualSettlementApprove(String ssn, SettlementApproveReqDto approveReqDto) {
        try {
            PromissoryRequest savedRequest = requestDaoService.getRequestBy(approveReqDto.getRequestId());

            PromissoryClientResponseDto<GuaranteeAndSettlementApproveResDto> issueApproveRes = promissoryClient.gradualSettlementApprove(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    GuaranteeAndSettlementApproveReqDto.builder()
                            .requestId(approveReqDto.getRequestId())
                            .signedPdf(approveReqDto.getSignatureValue())
                            .build());

            logDaoService.saveLogs("", "",
                    approveReqDto.getRequestId(), DataConverter.convertResponseToJson(issueApproveRes), "gradualSettlementApprove", "SUCCESSFUL");

            requestDaoService.updatePromissoryRequestStatus(savedRequest, RequestStatus.GRADUAL_SETTLEMENT_APPROVED);

            SettlementApproveResDto response = new SettlementApproveResDto();
            response.setOwnerNN(ssn);
            response.setPromissoryId(savedRequest.getPromissory().getPromissoryUid());
//            response.setSettlementAmount();
            response.setRequestDate(DataConverter.currentUTC());
            response.setRequestId(approveReqDto.getRequestId());
            return response;
        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    approveReqDto.getRequestId(),
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "gradualSettlementApprove", "EXCEPTION");

            promissoryBaseService.cancelRequest(approveReqDto.getRequestId());
            throw e;
        }
    }


    @Override
    public IssueAndGuaranteeAndSettlementRegisterResDto completeSettlementRegister(String authToken, String ssn, SettlementRegisterReqDto settlementRegisterReqDto) {
        settlementRegisterReqDto.setOwnerNN(ssn);
        PromissoryClientResponseDto<GuaranteeAndSettlementRegisterResDto> settlementRes = null;
        String requestId = null;
        try {
            settlementRes = promissoryClient.completeSettlementRegister(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    mapper.toRegisterSettlementReqDto(settlementRegisterReqDto));

            requestId = settlementRes.getInfo().getRequestId();
            logDaoService.saveLogs("", "",
                    requestId, DataConverter.convertResponseToJson(settlementRes), "completeSettlementRegister", "SUCCESSFUL");


            IssueAndGuaranteeAndSettlementRegisterResDto res = certificationService.certificationFlowToRegister(authToken, requestId, settlementRes.getInfo().getUnSignedPdf());

            //TODO: where should I save amount in DB?
            Promissory savedPromissory = promissoryDaoService.getPromissoryBy(settlementRegisterReqDto.getPromissoryId());
            requestDaoService.createPromissoryRequest(settlementRes.getInfo().getRequestId(),
                    savedPromissory, RequestType.COMPLETE_SETTLEMENT, RequestStatus.COMPLETE_SETTLEMENT_REGISTERED);

            return res;

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    requestId,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "completeSettlementRegister", "EXCEPTION");

            if (settlementRes != null && settlementRes.getInfo() != null)
                promissoryBaseService.cancelRequest(settlementRes.getInfo().getRequestId());

            throw e;
        }
    }

    @Override
    public SettlementApproveResDto completeSettlementApprove(String ssn, SettlementApproveReqDto approveReqDto) {
        try {
            PromissoryRequest savedRequest = requestDaoService.getRequestBy(approveReqDto.getRequestId());

            PromissoryClientResponseDto<GuaranteeAndSettlementApproveResDto> issueApproveRes = promissoryClient.completeSettlementApprove(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    GuaranteeAndSettlementApproveReqDto.builder()
                            .requestId(approveReqDto.getRequestId())
                            .signedPdf(approveReqDto.getSignatureValue())
                            .build());

            logDaoService.saveLogs("", "",
                    approveReqDto.getRequestId(), DataConverter.convertResponseToJson(issueApproveRes), "completeSettlementApprove", "SUCCESSFUL");

            requestDaoService.updatePromissoryRequestStatus(savedRequest, RequestStatus.COMPLETE_SETTLEMENT_APPROVED);

            SettlementApproveResDto response = new SettlementApproveResDto();
            response.setOwnerNN(ssn);
            response.setPromissoryId(savedRequest.getPromissory().getPromissoryUid());
//            response.setSettlementAmount();
            response.setRequestDate(DataConverter.currentUTC());
            response.setRequestId(approveReqDto.getRequestId());
            return response;
        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    approveReqDto.getRequestId(),
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "completeSettlementApprove", "EXCEPTION");

            promissoryBaseService.cancelRequest(approveReqDto.getRequestId());
            throw e;
        }
    }
}
