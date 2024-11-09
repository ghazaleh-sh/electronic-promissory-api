package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IssueRegisterResDto {
    /**
     * شناسه یکتای 30 رقمی پرداخت
     */
    private String requestId;
    /**
     * رشته PDF اولیه درخواست در قالب 64b
     */
    private String unSignedPdf;
    /**
     * شناسه یکتای 16 رقمی سفته
     */
    private String promissoryId;
    /**
     * کارمزد
     */
    private BigDecimal fee;
    /**
     * مبلغ تمبر مالیاتی که لازم است صادرکننده پرداخت کند
     */
    private BigDecimal stampDuty;
    /**
     * جمع مبلغ کارمزد + مبلغ تمبر مالیاتی
     */
    private BigDecimal feeAndStampTotal;

}
