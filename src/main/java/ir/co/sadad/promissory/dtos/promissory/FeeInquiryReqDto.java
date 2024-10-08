package ir.co.sadad.promissory.dtos.promissory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeeInquiryReqDto {

    private Long amount;
}
