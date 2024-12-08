package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.dtos.IssueAndGuaranteeAndSettlementRegisterResDto;
import ir.co.sadad.promissory.dtos.promissory.SettlementApproveReqDto;
import ir.co.sadad.promissory.dtos.promissory.SettlementApproveResDto;
import ir.co.sadad.promissory.dtos.promissory.SettlementRegisterReqDto;

public interface PromissorySettlementService {

    IssueAndGuaranteeAndSettlementRegisterResDto gradualSettlementRegister(String authToken, String ssn, SettlementRegisterReqDto settlementRegisterReqDto);

    SettlementApproveResDto gradualSettlementApprove(String ssn, SettlementApproveReqDto approveReqDto);

    IssueAndGuaranteeAndSettlementRegisterResDto completeSettlementRegister(String authToken, String ssn, SettlementRegisterReqDto settlementRegisterReqDto);

    SettlementApproveResDto completeSettlementApprove(String ssn, SettlementApproveReqDto approveReqDto);
}
