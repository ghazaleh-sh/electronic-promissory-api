package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GradualSettlementRegisterReqDto {
    /**
     * نوع شخص دارنده سفته (حقیقی یا حقوقی)
     */
    private String ownerType;

    /**
     * شماره/شناسه ملی دارنده سفته
     */
    private String ownerNN;

    /**
     * مبلغ تسویه تدریجی این فیلد میتواند از 1
     * تا مبلغ سفته منهای 1 ریال باشد
     * <p>
     * <p>
     * مبلغ تسویه کامل این فیلد مقدار 0 تا بینهایت (حتی
     * بیشتر از مبلغ سفته) را می پذیرد
     */
    private BigDecimal settlementAmount;

    private String promissoryId;

    private Object extraData;
}
