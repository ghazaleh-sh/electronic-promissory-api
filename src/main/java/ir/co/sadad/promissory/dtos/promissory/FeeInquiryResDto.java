package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class FeeInquiryResDto {
    private Long fee;
    private Long stampDuty;
    private Long feeAndStampTotal;
}
