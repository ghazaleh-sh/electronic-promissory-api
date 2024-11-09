package ir.co.sadad.promissory.providers.promissory;

import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.providers.promissory.configs.PromissoryClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "PromissoryClient", url = "${client.promissory.base-url}", configuration = {PromissoryClientConfig.class})
public interface PromissoryClient {

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.fee-inquiry}")
    PromissoryClientResponseDto<FeeInquiryResDto> feeInquiry(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody FeeInquiryReqDto feeInquiryReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.issue-register}")
    PromissoryClientResponseDto<IssueRegisterResDto> publicRegister(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody IssueRegisterReqDto registerReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.issue-approve}")
    PromissoryClientResponseDto<IssueApproveResDto> publicApprove(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody IssueApproveReqDto approveReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.cancel-request}")
    PromissoryClientResponseDto<Void> cancelRequest(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody CancelRequestReqDto cancelReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.cartable}")
    PromissoryClientResponseDto<DemandListResDto> cartable(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody DemandListReqDto listReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.inquiry}")
    PromissoryClientResponseDto<InquiryDetailResDto> inquiry(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody InquiryReqDto inquiryReqDto);

    @RequestMapping(method = RequestMethod.GET, value = "${client.promissory.fetch-signedPdf}")
    PromissoryClientResponseDto<FetchSignedPdfResDto> fetchSignedPdf(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody FetchSignedPdfReqDto fetchSignedPdfReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.my-promissory-action}")
    PromissoryClientResponseDto<MyPromissoryClientResDto> myPromissory(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody MyPromissoryClientReqDto myPromissoryClientReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.guarantee-preregister}")
    PromissoryClientResponseDto<GuaranteePreRegisterResDto> guaranteePreRegister(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody GuaranteePreRegisterReqDto preRegisterReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.guarantee-register}")
    PromissoryClientResponseDto<GuaranteeRegisterResDto> guaranteeRegister(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody GuaranteeRegisterReqDto registerReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.guarantee-preregister-cancel}")
    PromissoryClientResponseDto<Void> guaranteePreRegisterCancel(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody GuaranteeCancelReqDto guaranteeCancelReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.guarantee-approve}")
    PromissoryClientResponseDto<GuaranteeApproveResDto> guaranteeApprove(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody GuaranteeApproveReqDto approveReqDto);

    @RequestMapping(method = RequestMethod.POST, value = "${client.promissory.guarantee-list}")
    PromissoryClientResponseDto<GuaranteeListResDto> guaranteeList(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("terminalId") int terminalId,
            @RequestBody GuaranteeListReqDto listReqDto);
}
