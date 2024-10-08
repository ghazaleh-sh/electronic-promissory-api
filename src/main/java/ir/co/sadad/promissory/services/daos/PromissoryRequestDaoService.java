package ir.co.sadad.promissory.services.daos;

import ir.co.sadad.hambaam.persiandatetime.PersianUTC;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.RequestType;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.entities.Promissory;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.repositories.PromissoryRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * DAO for managing requests information
 * and managing junction table between request and stakeholders
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class PromissoryRequestDaoService {
    private final PromissoryRequestRepository promissoryRequestRepository;

    public PromissoryRequest getRequestBy(String requestUid) {
        return promissoryRequestRepository.findByRequestUid(requestUid).orElseThrow(() -> new PromissoryException("REQUEST_ID_IS_INVALID", HttpStatus.BAD_REQUEST));
    }

    public List<PromissoryRequest> getRequestByPromissoryUid(String promissoryUid, RequestType reqType) {
        return promissoryRequestRepository.findByPromissory_PromissoryUidAndRequestType(promissoryUid, reqType)
                .orElseThrow(() -> new PromissoryException("PROMISSORY_ID_IS_INVALID", HttpStatus.BAD_REQUEST));
    }

    @Transactional(propagation = Propagation.NESTED)
    public PromissoryRequest createPromissoryRequest(String requestId, Promissory promissory,
                                                     RequestType reqType, RequestStatus reqStatus) {
        PromissoryRequest promissoryReq = new PromissoryRequest();
        promissoryReq.setRequestUid(requestId);
        promissoryReq.setRequestType(reqType);
        promissoryReq.setRequestDate(PersianUTC.currentUTC().toString());
        promissoryReq.setRequestStatus(reqStatus);
        promissoryReq.setPromissory(promissory);
        return promissoryRequestRepository.saveAndFlush(promissoryReq);

    }

    public void updatePromissoryRequestStatus(PromissoryRequest savedPromissoryReq, RequestStatus requestStatus) {
        savedPromissoryReq.setRequestStatus(requestStatus);
        promissoryRequestRepository.saveAndFlush(savedPromissoryReq);
    }

    public void updatePromissoryRequestStatusAndIdForGuaranteeRegister(PromissoryRequest savedPromissoryReq, RequestStatus requestStatus, String newRequestId) {
        savedPromissoryReq.setRequestStatus(requestStatus);
        savedPromissoryReq.setRequestUid(newRequestId);
        promissoryRequestRepository.saveAndFlush(savedPromissoryReq);
    }


}
