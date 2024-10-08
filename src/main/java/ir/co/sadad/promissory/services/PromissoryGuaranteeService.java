package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.AddGuaranteeRegisterReqDto;
import ir.co.sadad.promissory.dtos.AddGuaranteeReqDto;
import ir.co.sadad.promissory.dtos.GuaranteeApproveFinalResDto;
import ir.co.sadad.promissory.dtos.IssueAndGuaranteeRegisterResDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeApproveReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteePreRegisterResDto;

public interface PromissoryGuaranteeService {

    GuaranteePreRegisterResDto guaranteePreregister(String ssn, AddGuaranteeReqDto addGuaranteeReqDto);

    IssueAndGuaranteeRegisterResDto guaranteeRegister(String authToken, String ssn, AddGuaranteeRegisterReqDto registerReqDto);

    GuaranteeApproveFinalResDto guaranteeApprove(String ssn, GuaranteeApproveReqDto approveReqDto);

    void guaranteeCancel(String ssn, String promissoryId);
}
