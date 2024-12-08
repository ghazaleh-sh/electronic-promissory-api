package ir.co.sadad.promissory.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.services.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ir.co.sadad.promissory.commons.Constants.OTP;
import static ir.co.sadad.promissory.commons.Constants.SSN;

@RestController
@RequestMapping(path = "${v1API}/promissory")
@Tag(description = "سرویس های سفته", name = "promissory services")
@RequiredArgsConstructor
public class PromissoryController {

    private final PromissoryService promissoryService;
    private final PromissoryInfoService promissoryInfoService;
    private final PromissoryGuaranteeService guaranteeService;
    private final PromissoryBaseService baseService;
    private final PromissorySettlementService settlementService;

    @Operation(summary = "سرویس استعلام کارمزد")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FeeInquiryResDto.class)))
    @GetMapping("/fee")
    public ResponseEntity<FeeInquiryResDto> feeInquiry(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                       @RequestParam(name = "promissoryAmount") Long promissoryAmount) {
        return new ResponseEntity<>(promissoryService.feeInquiry(authToken, promissoryAmount), HttpStatus.OK);

    }

    @Operation(summary = "سرویس صدور اولیه")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = IssueAndGuaranteeAndSettlementRegisterResDto.class)))
    @PostMapping("/register")
    public ResponseEntity<IssueAndGuaranteeAndSettlementRegisterResDto> publishRegister(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                                        @RequestHeader(SSN) String ssn,
                                                                                        @Valid @RequestBody IssueFirstReqDto firstReqDto) {
        return new ResponseEntity<>(promissoryService.issueRegister(authToken, ssn, firstReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس صدور نهایی")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = IssueSecondResDto.class)))
    @PostMapping("/approve")
    public ResponseEntity<IssueSecondResDto> publishApprove(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                            @RequestHeader(SSN) String ssn,
                                                            @RequestHeader(value = OTP, required = false) String otpCode,
                                                            @Valid @RequestBody IssueSecondReqDto secondReqDto) {
        return new ResponseEntity<>(promissoryService.issueApprove(authToken, secondReqDto, otpCode), HttpStatus.OK);

    }

    @Operation(summary = "سرویس حذف درخواست")
    @ApiResponse(responseCode = "204")
    @PostMapping("/cancel/{requestId}")
    public ResponseEntity<HttpStatus> requestCancel(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                    @RequestHeader(SSN) String ssn,
                                                    @PathVariable("requestId") String requestId) {
        baseService.cancelRequest(requestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "سرویس کارتابل سفته (فقط آیتمهای مربوط به ضامن جهت تایید یا رد درخواست را نمایش میدهیم.)")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = CartableResDto.class)))
    @GetMapping("/cartable")
    public ResponseEntity<List<CartableResDto>> cartable(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                         @RequestHeader(SSN) String ssn,
                                                         @RequestParam(name = "page_number", defaultValue = "1") Integer page_number,
                                                         @RequestParam(name = "page_size", defaultValue = "10") Integer page_size) {
        return new ResponseEntity<>(promissoryInfoService.cartable(ssn, page_number, page_size), HttpStatus.OK);

    }

    @Operation(summary = "سرویس استعلام سفته")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = InquiryResDto.class)))
    @GetMapping("/inquiry")
    public ResponseEntity<InquiryResDto> inquiry(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                 @Valid @RequestBody InquiryReqDto inquiryReqDto) {
        return new ResponseEntity<>(promissoryInfoService.inquiry(inquiryReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس استعلام جزئیات سفته")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = InquiryResDto.class)))
    @GetMapping("/inquiry-detail/{promissoryId}")
    public ResponseEntity<InquiryResDto> inquiryDetails(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                        @RequestHeader(SSN) String ssn,
                                                        @PathVariable(name = "promissoryId") String promissoryId) {
        return new ResponseEntity<>(promissoryInfoService.inquiryDetails(ssn, promissoryId), HttpStatus.OK);

    }

    @Operation(summary = "سرویس واکشی سند سفته")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FetchSignedPdfResDto.class)))
    @GetMapping("/fetch")
    public ResponseEntity<FetchSignedPdfResDto> fetchDocument(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                              @Valid @RequestBody FetchDocumentReqDto documentReqDto) {
        return new ResponseEntity<>(promissoryInfoService.fetchSignedPdf(documentReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس سفته های من")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = MyPromissoryResDto.class)))
    @GetMapping("/my-promissory")
    public ResponseEntity<List<MyPromissoryResDto>> myPromissory(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                 @RequestHeader(SSN) String ssn,
                                                                 @RequestParam(name = "page_number", defaultValue = "1") Integer page_number,
                                                                 @RequestParam(name = "page_size", defaultValue = "10") Integer page_size,
                                                                 @RequestParam(name = "role", required = false) String role) {
        return new ResponseEntity<>(promissoryInfoService.myPromissory(ssn, page_number, page_size, role != null ? StakeholderRole.valueOf(role) : null), HttpStatus.OK);

    }


    //************* guarantor services ***************//
    @Operation(summary = "سرویس افزودن ضامن")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GuaranteePreRegisterResDto.class)))
    @PostMapping("/guarantor/add")
    public ResponseEntity<GuaranteePreRegisterResDto> addGuarantor(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                   @RequestHeader(SSN) String ssn,
                                                                   @Valid @RequestBody AddGuaranteeReqDto addGuaranteeReqDto) {
        return new ResponseEntity<>(guaranteeService.guaranteePreregister(ssn, addGuaranteeReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس تایید ضمانت اولیه")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = IssueAndGuaranteeAndSettlementRegisterResDto.class)))
    @PostMapping("/guarantor/register")
    public ResponseEntity<IssueAndGuaranteeAndSettlementRegisterResDto> guaranteeRegister(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                                          @RequestHeader(SSN) String ssn,
                                                                                          @Valid @RequestBody AddGuaranteeRegisterReqDto registerReqDto) {
        return new ResponseEntity<>(guaranteeService.guaranteeRegister(authToken, ssn, registerReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس تایید ضمانت نهایی")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GuaranteeApproveFinalResDto.class)))
    @PostMapping("/guarantor/approve")
    public ResponseEntity<GuaranteeApproveFinalResDto> guaranteeApprove(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                        @RequestHeader(SSN) String ssn,
                                                                        @Valid @RequestBody GuaranteeAndSettlementApproveReqDto approveReqDto) {
        return new ResponseEntity<>(guaranteeService.guaranteeApprove(ssn, approveReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس حذف یا عدم تایید ضامن")
    @ApiResponse(responseCode = "204")
    @PostMapping("/guarantor/cancel")
    public ResponseEntity<HttpStatus> guaranteeCancel(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                      @RequestHeader(SSN) String ssn,
                                                      @Valid @RequestBody GuaranteeDeleteOrRejectReqDto guaranteeCancelReqDto) {
        guaranteeService.guaranteeCancel(ssn, guaranteeCancelReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "سرویس استعلام لیست ضامنین سفته")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = GuaranteeListResDto.class)))
    @PostMapping("/guarantor/list")
    public ResponseEntity<GuaranteeListResDto> guaranteeList(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                             @RequestBody GuaranteeListReqDto listReqDto) {
        return new ResponseEntity<>(guaranteeService.guaranteeList(listReqDto), HttpStatus.OK);

    }


    //************* settlement services ***************//
    @Operation(summary = "سرویس درخواست تسویه تدریجی")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = IssueAndGuaranteeAndSettlementRegisterResDto.class)))
    @PostMapping("/gradual-register")
    public ResponseEntity<IssueAndGuaranteeAndSettlementRegisterResDto> gradualRegister(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                                        @RequestHeader(SSN) String ssn,
                                                                                        @Valid @RequestBody SettlementRegisterReqDto registerReqDto) {
        return new ResponseEntity<>(settlementService.gradualSettlementRegister(authToken, ssn, registerReqDto), HttpStatus.OK);
    }


    @Operation(summary = "سرویس نهایی تسویه تدریجی")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SettlementApproveResDto.class)))
    @PostMapping("/gradual-approve")
    public ResponseEntity<SettlementApproveResDto> gradualApprove(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                  @RequestHeader(SSN) String ssn,
                                                                  @Valid @RequestBody SettlementApproveReqDto settlementApproveReqDto) {
        return new ResponseEntity<>(settlementService.gradualSettlementApprove(authToken, settlementApproveReqDto), HttpStatus.OK);

    }

    @Operation(summary = "سرویس درخواست تسویه کامل")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = IssueAndGuaranteeAndSettlementRegisterResDto.class)))
    @PostMapping("/complete-register")
    public ResponseEntity<IssueAndGuaranteeAndSettlementRegisterResDto> completeRegister(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                                         @RequestHeader(SSN) String ssn,
                                                                                         @Valid @RequestBody SettlementRegisterReqDto registerReqDto) {
        return new ResponseEntity<>(settlementService.completeSettlementRegister(authToken, ssn, registerReqDto), HttpStatus.OK);
    }

    @Operation(summary = "سرویس نهایی تسویه کامل")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SettlementApproveResDto.class)))
    @PostMapping("/complete-approve")
    public ResponseEntity<SettlementApproveResDto> completeApprove(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authToken,
                                                                   @RequestHeader(SSN) String ssn,
                                                                   @Valid @RequestBody SettlementApproveReqDto settlementApproveReqDto) {
        return new ResponseEntity<>(settlementService.completeSettlementApprove(authToken, settlementApproveReqDto), HttpStatus.OK);

    }
}

