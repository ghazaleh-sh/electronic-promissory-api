package ir.co.sadad.promissory.dtos.promissory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * اطلاعات تسویه
 */
@Data
public class SettlementInfo {
    private BigDecimal settlementAmount;
    private String settlementType;
    /**
     * شماره تسویه سفته (درصورتیکه نوع آن تسویه تدریجی باشد)
     */
    private String number;
    private String creationDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String creationTime;
}
