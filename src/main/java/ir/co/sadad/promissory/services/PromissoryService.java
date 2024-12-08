package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.IssueFirstReqDto;
import ir.co.sadad.promissory.dtos.IssueAndGuaranteeAndSettlementRegisterResDto;
import ir.co.sadad.promissory.dtos.IssueSecondReqDto;
import ir.co.sadad.promissory.dtos.IssueSecondResDto;
import ir.co.sadad.promissory.dtos.promissory.FeeInquiryResDto;

public interface PromissoryService {

    FeeInquiryResDto feeInquiry(String authToken, Long promissoryAmount);

    IssueAndGuaranteeAndSettlementRegisterResDto issueRegister(String authToken, String issuerSSN, IssueFirstReqDto issueReqDto);

    IssueSecondResDto issueApprove(String authToken, IssueSecondReqDto secondReqDto, String otp);
}
