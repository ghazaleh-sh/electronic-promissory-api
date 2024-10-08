package ir.co.sadad.promissory.services;

import ir.co.sadad.hambaam.persiandatetime.PersianUTC;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.RequestType;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.AddGuaranteeRegisterReqDto;
import ir.co.sadad.promissory.dtos.AddGuaranteeReqDto;
import ir.co.sadad.promissory.dtos.GuaranteeApproveFinalResDto;
import ir.co.sadad.promissory.dtos.IssueAndGuaranteeRegisterResDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static ir.co.sadad.promissory.commons.Constants.GUARANTEE_CANCEL_DEMAND_TYPE;
import static ir.co.sadad.promissory.commons.Constants.TERMINAL_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromissoryGuaranteeServiceImpl implements PromissoryGuaranteeService {

    private final PromissoryClient promissoryClient;
    private final SSOTokenService promissoryTokenService;
    private final CertificationService certificationService;
    private final PromissoryBaseService promissoryBaseService;
    private final PromissoryMapper mapper;

    private final PromissoryDaoService promissoryDaoService;
    private final PromissoryRequestDaoService requestDaoService;
    private final PromissoryStakeholderDaoService stakeholderDaoService;
    private final PromissoryLogDaoService logDaoService;

    @Override
    public GuaranteePreRegisterResDto guaranteePreregister(String ssn, AddGuaranteeReqDto addGuaranteeReqDto) {

        Promissory savedPromissory = promissoryDaoService.getPromissoryBy(addGuaranteeReqDto.getPromissoryId());

        PromissoryClientResponseDto<GuaranteePreRegisterResDto> preregisterRes = null;
        String demandRequestId = null;
        try {
            preregisterRes = promissoryClient.guaranteePreRegister(promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    mapper.toPreregisterReqDto(ssn, addGuaranteeReqDto));

            demandRequestId = preregisterRes.getInfo().getDemandRequestId();
            logDaoService.saveLogs("", "",
                    demandRequestId, DataConverter.convertResponseToJson(preregisterRes), "guaranteePreregister", "SUCCESSFUL");


            PromissoryRequest newRequest = requestDaoService.createPromissoryRequest(demandRequestId, savedPromissory,
                    RequestType.GUARANTEE, RequestStatus.WAITING_FOR_GUARANTOR_APPROVED);

            stakeholderDaoService.createStakeholderForGuarantor(addGuaranteeReqDto.getGuaranteeNN(), newRequest);

            return preregisterRes.getInfo();

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    demandRequestId,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "guaranteePreregister", "EXCEPTION");

            if (preregisterRes != null && demandRequestId != null)
                guaranteeCancel(ssn, demandRequestId);

            throw e;
        }
    }

    @Override
    public IssueAndGuaranteeRegisterResDto guaranteeRegister(String authToken, String ssn, AddGuaranteeRegisterReqDto registerReqDto) {
//TODO: why client does not give me pre-request Id?
        PromissoryRequest savedRequestForCurrentUser = requestDaoService.getRequestByPromissoryUid(registerReqDto.getPromissoryId(), RequestType.GUARANTEE)
                .stream()
                .filter(request -> RequestStatus.WAITING_FOR_GUARANTOR_APPROVED.equals(request.getRequestStatus()))
                .filter(req -> ssn.equals(stakeholderDaoService.getStakeholderByRole(req, StakeholderRole.GUARANTOR).getNationalNumber()))
                .findFirst()
                .orElseThrow(() -> new PromissoryException("GUARANTOR_NOT_FOUND", HttpStatus.BAD_REQUEST));

        PromissoryClientResponseDto<GuaranteeRegisterResDto> registerRes = null;
        String requestId = null;
        try {
            GuaranteeRegisterReqDto registerReq = mapper.toRegisterReqDto(ssn, registerReqDto);

            registerRes = promissoryClient.guaranteeRegister(promissoryTokenService.getToken(),
                    TERMINAL_ID, registerReq);

            requestId = registerRes.getInfo().getRequestId();
            logDaoService.saveLogs("", "",
                    savedRequestForCurrentUser.getRequestUid(), DataConverter.convertResponseToJson(registerRes), "guaranteeRegister", "SUCCESSFUL");

            stakeholderDaoService.updateStakeholderForGuarantor(registerReq, savedRequestForCurrentUser);

            IssueAndGuaranteeRegisterResDto res = certificationService.certificationFlowToRegister(authToken, requestId, registerRes.getInfo().getUnSignedPdf());

            requestDaoService.updatePromissoryRequestStatusAndIdForGuaranteeRegister(savedRequestForCurrentUser,
                    RequestStatus.REGISTERED_WAITING_FOR_GUARANTOR_APPROVED,
                    requestId);

            return res;

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    savedRequestForCurrentUser != null ? savedRequestForCurrentUser.getRequestUid() : null,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "guaranteeRegister", "EXCEPTION");

            if (registerRes != null && requestId != null)
                promissoryBaseService.cancelRequest(registerRes.getInfo().getRequestId());

            throw e;
        }
    }

    @Override
    public GuaranteeApproveFinalResDto guaranteeApprove(String ssn, GuaranteeApproveReqDto approveReqDto) {
        PromissoryRequest savedRequest = requestDaoService.getRequestBy(approveReqDto.getRequestId());
        PromissoryStakeholder guarantor = stakeholderDaoService.getStakeholderByRole(savedRequest, StakeholderRole.GUARANTOR);
        Promissory savedPromissory = savedRequest.getPromissory();

        PromissoryClientResponseDto<GuaranteeApproveResDto> approveRes = null;
        try {
            approveRes = promissoryClient.guaranteeApprove(promissoryTokenService.getToken(),
                    TERMINAL_ID, approveReqDto);

            logDaoService.saveLogs("", "",
                    approveReqDto.getRequestId(), DataConverter.convertResponseToJson(approveRes), "guaranteeApprove", "SUCCESSFUL");

            requestDaoService.updatePromissoryRequestStatus(savedRequest, RequestStatus.GUARANTOR_APPROVED);

            PromissoryStakeholder issuer = stakeholderDaoService.getStakeholderByRole(
                    requestDaoService.getRequestByPromissoryUid(savedPromissory.getPromissoryUid(), RequestType.ISSUE)
                            .stream().findFirst()
                            .orElseThrow(() -> new PromissoryException("ISSUER_NOT_FOUND", HttpStatus.BAD_REQUEST)),
                    StakeholderRole.ISSUER);

            GuaranteeApproveFinalResDto finalRes = new GuaranteeApproveFinalResDto();
            finalRes.setGuaranteeNN(ssn);
            finalRes.setRequestId(approveReqDto.getRequestId());
            finalRes.setGuaranteeCellphone(guarantor.getCellphone());
            finalRes.setGuaranteeAddress(guarantor.getAddress());
            finalRes.setGuaranteeAccountNumber(guarantor.getAccountNumber());
            finalRes.setGuaranteeFullName(guarantor.getFullName());
            finalRes.setPaymentPlace(guarantor.getPaymentPlace());
            finalRes.setPromissoryId(savedPromissory.getPromissoryUid());
            finalRes.setDescription(savedPromissory.getDescription());
            finalRes.setOwnerNN(issuer.getNationalNumber());
            finalRes.setOwnerFullName(issuer.getFullName());
            finalRes.setConfirmDate(PersianUTC.currentUTC().toString());

            return finalRes;

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    savedRequest.getRequestUid(),
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "guaranteeApprove", "EXCEPTION");

            promissoryBaseService.cancelRequest(approveReqDto.getRequestId());

            throw e;
        }

    }

    @Override
    @SneakyThrows
    public void guaranteeCancel(String ssn, String preGuaranteeRequestId) {
        PromissoryRequest savedRequest = null;
        try {
            savedRequest = requestDaoService.getRequestBy(preGuaranteeRequestId);
            List<PromissoryStakeholder> savedStakeholders = stakeholderDaoService.getStakeholders(savedRequest);
            boolean isGuarantor = savedStakeholders.stream().anyMatch(stakeholder -> stakeholder.getNationalNumber().equals(ssn));

            GuaranteeCancelReqDto req = new GuaranteeCancelReqDto();
            req.setPromissoryId(savedRequest.getPromissory().getPromissoryUid());
            req.setNationalNumber(ssn);
            req.setPersonType(DataConverter.personTypeConverter(ssn));
            req.setRequestId(preGuaranteeRequestId);
            req.setDemandType(GUARANTEE_CANCEL_DEMAND_TYPE);
            req.setDemandState(isGuarantor ? RequestStatus.REJECTED.toString() : RequestStatus.CANCELED.toString());

            PromissoryClientResponseDto<Void> cancelRes = promissoryClient.guaranteePreRegisterCancel(
                    promissoryTokenService.getToken(),
                    TERMINAL_ID,
                    req);

            logDaoService.saveLogs("", "",
                    savedRequest.getRequestUid(), DataConverter.convertResponseToJson(cancelRes), "guaranteeCancel", "SUCCESSFUL");

            requestDaoService.updatePromissoryRequestStatus(savedRequest, isGuarantor ? RequestStatus.REJECTED : RequestStatus.CANCELED);

        } catch (Exception e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    savedRequest != null ? savedRequest.getRequestUid() : null,
                    e instanceof PromissoryException ? ((PromissoryException) e).getJsonError() : null,
                    "guaranteeCancel", "EXCEPTION");

        }
    }

}
