package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class GuaranteeCancelReqDto {
    private String personType;
    private String nationalNumber;
    private String promissoryId;
    private String demandType;
    private String demandState;
    private String requestId;
    private Object extraData;
}
