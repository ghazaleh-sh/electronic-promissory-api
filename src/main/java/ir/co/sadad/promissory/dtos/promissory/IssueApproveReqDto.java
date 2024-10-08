package ir.co.sadad.promissory.dtos.promissory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueApproveReqDto {
    /**
     * شناسه یکتای 30 رقمی پرداخت
     */
    private String requestId;
    /**
     * شناسه مرجع پرداخت فیش مالیاتی(RRN پرداختی)طول این فیلد باید بین 10 تا 20کاراکتر باشد
     */
    private String paymentId;
    /**
     * رشته PDF امضا شده توسط صادرکننده
     * سفته در قالب base64
     */
    private String signedPdf;
    /**
     * شناسه مرجع پرداخت کارمزد
     */
    private Long feeTransId;
    /**
     * روش پرداخت وجه تمبر مالیاتی و کارمزد بانک
     * (در صورتی که این فیلد مقداردهی نشود پرداخت به‌صورت انتقال وجه از حساب
     * مشتری بانک ملی که صادرکننده سفته است صورت میگیرد.) = BUSINESS
     */
    private String PaymentMode;
    /**
     * اطلاعات اختیاری ThirdParty
     */
    private Object extraData;
}
