package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.enums.*;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.CartableResDto;
import ir.co.sadad.promissory.dtos.FetchDocumentReqDto;
import ir.co.sadad.promissory.dtos.InquiryResDto;
import ir.co.sadad.promissory.dtos.MyPromissoryResDto;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.entities.Promissory;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.mapper.PromissoryInfoMapper;
import ir.co.sadad.promissory.providers.promissory.PromissoryClient;
import ir.co.sadad.promissory.providers.sso.services.SSOTokenService;
import ir.co.sadad.promissory.services.daos.PromissoryDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryLogDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryRequestDaoService;
import ir.co.sadad.promissory.services.daos.PromissoryStakeholderDaoService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ir.co.sadad.promissory.commons.Constants.TERMINAL_ID;
import static ir.co.sadad.promissory.services.utils.DataConverter.getLocalizedActions;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromissoryInfoServiceImpl implements PromissoryInfoService {

    private final PromissoryClient promissoryClient;
    private final SSOTokenService promissoryTokenService;
    private final PromissoryDaoService promissoryDaoService;

    private final PromissoryInfoMapper infoMapper;
    private final PromissoryLogDaoService logDaoService;
    private final PromissoryRequestDaoService requestDaoService;
    private final PromissoryStakeholderDaoService stakeholderDaoService;


    @SneakyThrows
    @Override
    public List<CartableResDto> cartable(String ssn, int page_number, int page_size) {

        List<DemandListResDto.Item> demandListRes = null;
        try {
            demandListRes = promissoryClient.cartable(promissoryTokenService.getToken(),
                            TERMINAL_ID,
                            infoMapper.toDemandListReqDto(ssn))
                    .getInfo()
                    .getItems();

            // to filter just Guarantee items for guarantors , others cannot see anything in their cartable
            demandListRes = demandListRes.stream().filter(item -> {
                        PromissoryRequest savedGuaranteeReq = requestDaoService.getRequestBy(item.getUid().toString());
                        return RequestType.GUARANTEE.equals(savedGuaranteeReq.getRequestType()) &&
                                ssn.equals(stakeholderDaoService.getStakeholderByRole(savedGuaranteeReq, StakeholderRole.GUARANTOR).getNationalNumber());
                    })
                    .toList();

        } catch (PromissoryException e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    null, e.getJsonError(),
                    "cartable", "EXCEPTION");

            throw e;
        }

//        if (request_status != null)
//            demandListRes = demandListRes.stream()
//                    .filter(item -> item.getState().equals(request_status))
//                    .toList();

        int totalItems = demandListRes.size();
        int startIndex = (page_number - 1) * page_size;
        int endIndex = Math.min(startIndex + page_size, totalItems);

        startIndex = Math.max(startIndex, 0);  // ensure startIndex is not negative
        endIndex = Math.min(endIndex, totalItems);

        try {
            demandListRes = demandListRes.subList(startIndex, endIndex);
        } catch (Exception e) {
            throw new PromissoryException("INVALID_INDEX_RANGE", HttpStatus.BAD_REQUEST);
        }

        //        res.forEach(cartableResDto -> {
//            if (cartableResDto.getActions().stream().anyMatch(action -> action.equals(RequestAction.CANCEL))) {
//                if (ssn.equals(cartableResDto.getRequesterValue())) {
//                    cartableResDto.setCancelType(CancelType.DELETE);
//                    cartableResDto.setLocalizedCancelType(CancelType.DELETE.getDescription());
//                } else {
//                    cartableResDto.setCancelType(CancelType.REJECT);
//                    cartableResDto.setLocalizedCancelType(CancelType.REJECT.getDescription());
//                }
//            }
//        });

        return infoMapper.toCartableResDto(demandListRes);
    }

    @SneakyThrows
    @Override
    public InquiryResDto inquiry(InquiryReqDto inquiryReqDto) {
        InquiryDetailResDto inquiryRes = null;
        try {
            inquiryRes = promissoryClient.inquiry(promissoryTokenService.getToken(),
                            TERMINAL_ID,
                            inquiryReqDto)
                    .getInfo();
        } catch (PromissoryException e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    null, e.getJsonError(),
                    "inquiry", "EXCEPTION");

            throw e;
        }

        return infoMapper.toInquiryResDto(inquiryRes);
    }

    @SneakyThrows
    @Override
    public InquiryResDto inquiryDetails(String ssn, String promissoryId) {
        Promissory savedPromissory = promissoryDaoService.getPromissoryBy(promissoryId);

        InquiryResDto res = inquiry(InquiryReqDto.builder()
                .issuerNN(ssn)
                .promissoryId(promissoryId)
                .build());
        res.setPromissoryName(savedPromissory.getPromissoryName());
        return res;
    }

    @SneakyThrows
    @Override
    public FetchSignedPdfResDto fetchSignedPdf(FetchDocumentReqDto documentReqDto) {
        try {
            return promissoryClient.fetchSignedPdf(promissoryTokenService.getToken(),
                            TERMINAL_ID,
                            infoMapper.toFetchSignedReqDto(documentReqDto))
                    .getInfo();
        } catch (PromissoryException e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    null, e.getJsonError(),
                    "fetchSignedPdf", "EXCEPTION");

            throw e;
        }
    }

    @SneakyThrows
    @Override
    public List<MyPromissoryResDto> myPromissory(String ssn, int page_number, int page_size, StakeholderRole role) {
        int MAX_PAGE_SIZE = 100; // to get all list from the client and then filter based on params
        int DEFAULT_PAGE_NUMBER = 1;
        List<MyPromissoryClientResDto.MyPromissoryList> clientResList = null;
        try {
            clientResList = promissoryClient.myPromissory(promissoryTokenService.getToken(),
                            TERMINAL_ID,
                            MyPromissoryClientReqDto.builder()
                                    .nationalNumber(ssn)
                                    .role(role)
                                    .page(DEFAULT_PAGE_NUMBER)
                                    .size(MAX_PAGE_SIZE)
                                    .build())
                    .getInfo()
                    .getList();
        } catch (PromissoryException e) {
            logDaoService.saveLogs(e.getClass().getName(), e.getMessage(),
                    null, e.getJsonError(),
                    "myPromissory", "EXCEPTION");

            throw e;
        }

        //TODO: filter based on user's params, going to be added

        int totalItems = clientResList.size();
        int startIndex = (page_number - 1) * page_size;
        int endIndex = Math.min(startIndex + page_size, totalItems);

        startIndex = Math.max(startIndex, 0);  // ensure startIndex is not negative
        endIndex = Math.min(endIndex, totalItems);

        try {
            clientResList = clientResList.subList(startIndex, endIndex);
        } catch (Exception e) {
            throw new PromissoryException("INVALID_INDEX_RANGE", HttpStatus.BAD_REQUEST);
        }


        List<MyPromissoryResDto> res = infoMapper.toMyPromissoryListResDto(clientResList);
        res.forEach(eachPromissory -> {

            Promissory savedPr = promissoryDaoService.getOptionalPromissoryBy(eachPromissory.getPromissoryId());
            if (savedPr != null) {
                eachPromissory.setPromissoryName(savedPr.getPromissoryName());

                List<PromissoryRequest> savedGuaranteeRequests = requestDaoService.getRequestByPromissoryUid(savedPr.getPromissoryUid(), RequestType.GUARANTEE);
                eachPromissory.setGuarantorCount(savedGuaranteeRequests.size());

                AtomicInteger waitingRequest = new AtomicInteger();
                savedGuaranteeRequests.forEach(eachRequest -> {
                    if (RequestStatus.WAITING_FOR_GUARANTOR_APPROVED.equals(eachRequest.getRequestStatus()))
                        waitingRequest.getAndIncrement();
                });

                if (waitingRequest.get() > 1)
                    eachPromissory.setRequestStatus(RequestStatus.WAITING_FOR_GUARANTORS_APPROVED);
            }

//            String state = eachPromissory.getState();
//
//            // Default action
//            List<RequestAction> actions = List.of(RequestAction.DETAIL_VIEW);
//            List<String> localizedActions = List.of(RequestAction.DETAIL_VIEW.getDescription());
//
//            if (PromissoryState.F.getState().equals(state) || PromissoryState.G.getState().equals(state)) {
//                if (StakeholderRole.ISSUER.equals(eachPromissory.getRole())) {
//                    actions = List.of(RequestAction.ADD_GUARANTEE, RequestAction.DETAIL_VIEW);
//                    localizedActions = getLocalizedActions(actions);
//
//                } else if (StakeholderRole.CURRENT_OWNER.equals(eachPromissory.getRole())) {
//                    if (eachPromissory.getDueDate() != null) {
//                        actions = List.of(RequestAction.ENDORSEMENT, RequestAction.DETAIL_VIEW);
//                    } else {
//                        actions = List.of(RequestAction.ENDORSEMENT, RequestAction.SETTLEMENT, RequestAction.DETAIL_VIEW);
//                    }
//                    localizedActions = getLocalizedActions(actions);
//                }
//            }
//            eachPromissory.setAction(actions);
//            eachPromissory.setLocalizedAction(localizedActions);
        });

        return res;
    }

}
