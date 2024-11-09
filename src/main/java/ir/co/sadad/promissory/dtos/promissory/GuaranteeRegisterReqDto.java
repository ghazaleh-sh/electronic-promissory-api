package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class GuaranteeRegisterReqDto {

    private String guaranteeType;
    private String guaranteeNN;
    private String guaranteeCellphone;
    private String guaranteeAccountNumber;
    private String guaranteeFullName;
    private String guaranteeAddress;
    private String paymentPlace;
    private String description;
    private String issuerType;
    private String nationalNumber;
    private String promissoryId;
    private String extraData;

}
