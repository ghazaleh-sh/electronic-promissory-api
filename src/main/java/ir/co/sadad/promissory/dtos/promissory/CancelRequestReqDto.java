package ir.co.sadad.promissory.dtos.promissory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelRequestReqDto {
    private String requestId;
    private Object extraData;
}
