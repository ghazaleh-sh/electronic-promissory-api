package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeApproveReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeListReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeListResDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteePreRegisterResDto;

public interface PromissoryGuaranteeService {

    GuaranteePreRegisterResDto guaranteePreregister(String ssn, AddGuaranteeReqDto addGuaranteeReqDto);

    IssueAndGuaranteeRegisterResDto guaranteeRegister(String authToken, String ssn, AddGuaranteeRegisterReqDto registerReqDto);

    GuaranteeApproveFinalResDto guaranteeApprove(String ssn, GuaranteeApproveReqDto approveReqDto);

    void guaranteeCancel(String ssn, GuaranteeDeleteOrRejectReqDto guaranteeCancelReqDto);

    GuaranteeListResDto guaranteeList(GuaranteeListReqDto guaranteeListReqDto);
}
