package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class IssueApproveResDto {
    /**
     * شناسه یکتای سفته
     */
    private String promissoryId;
    /**
     * رشته PDF امضا شده توسط کارگزار، ذینفعان و وزارت اقتصاد در قالب base64
     */
    private String multiSignedPdf;
    /**
     * شناسه مرجع پرداخت کارمزد
     */
    private String feeTransId;
    /**
     * شناسه مرجع پرداخت تمبر مالیاتی
     */
    private String stampTransId;

}
