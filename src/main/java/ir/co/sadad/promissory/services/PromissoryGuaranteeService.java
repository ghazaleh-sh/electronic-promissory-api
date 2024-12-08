package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeAndSettlementApproveReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeListReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeListResDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteePreRegisterResDto;

public interface PromissoryGuaranteeService {

    GuaranteePreRegisterResDto guaranteePreregister(String ssn, AddGuaranteeReqDto addGuaranteeReqDto);

    IssueAndGuaranteeAndSettlementRegisterResDto guaranteeRegister(String authToken, String ssn, AddGuaranteeRegisterReqDto registerReqDto);

    GuaranteeApproveFinalResDto guaranteeApprove(String ssn, GuaranteeAndSettlementApproveReqDto approveReqDto);

    void guaranteeCancel(String ssn, GuaranteeDeleteOrRejectReqDto guaranteeCancelReqDto);

    GuaranteeListResDto guaranteeList(GuaranteeListReqDto guaranteeListReqDto);
}
