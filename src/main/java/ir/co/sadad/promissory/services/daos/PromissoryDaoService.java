package ir.co.sadad.promissory.services.daos;

import ir.co.sadad.hambaam.persiandatetime.PersianUTC;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.IssueFirstReqDto;
import ir.co.sadad.promissory.dtos.promissory.IssueApproveResDto;
import ir.co.sadad.promissory.dtos.promissory.IssueRegisterResDto;
import ir.co.sadad.promissory.entities.Promissory;
import ir.co.sadad.promissory.repositories.PromissoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * DAO for managing promissory information
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class PromissoryDaoService {
    private final PromissoryRepository promissoryRepository;

    public Promissory getPromissoryBy(String promissoryUid) {
        return promissoryRepository.findByPromissoryUid(promissoryUid).orElseThrow(() -> new PromissoryException("PROMISSORY_ID_IS_INVALID", HttpStatus.BAD_REQUEST));
    }

    public Promissory getOptionalPromissoryBy(String promissoryUid) {
        Optional<Promissory>  savedPromissory = promissoryRepository.findByPromissoryUid(promissoryUid);
        return savedPromissory.orElse(null);
    }

    public Promissory createPromissory(IssueFirstReqDto issueReqDto, IssueRegisterResDto resDto) {
        Promissory promissory = new Promissory();
        promissory.setPromissoryName(issueReqDto.getPromissoryName());
        promissory.setAmount(issueReqDto.getAmount());
        promissory.setTransferable(issueReqDto.getTransferable() != null ? issueReqDto.getTransferable() : true);
        promissory.setDueDate(issueReqDto.getDueDate());
        promissory.setPromissoryUid(resDto.getPromissoryId());
        promissory.setFee(resDto.getFee());
        promissory.setStampDuty(resDto.getStampDuty());
        promissory.setFeeAndStampTotal(resDto.getFeeAndStampTotal());
        promissory.setStatus(RequestStatus.REGISTERED);
        return promissoryRepository.saveAndFlush(promissory);

    }

    public Promissory updatePromissoryIssue(IssueApproveResDto resDto, String promissoryUid) {
        Promissory savedPromissory = getPromissoryBy(promissoryUid);

        savedPromissory.setFeeTransId(resDto.getFeeTransId());
        savedPromissory.setStampTransId(resDto.getStampTransId());
        savedPromissory.setPaymentDateTime(PersianUTC.currentUTC().toString());
        savedPromissory.setStatus(RequestStatus.PRIMITIVE);
        return promissoryRepository.saveAndFlush(savedPromissory);

    }
}
