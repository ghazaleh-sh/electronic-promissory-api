package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class GuaranteePreRegisterReqDto {

    private String guaranteeType;
    private String guaranteeNN;
    private String promissoryId;
    private String issuerType;
    private String nationalNumber;
    private Object extraData;

}
