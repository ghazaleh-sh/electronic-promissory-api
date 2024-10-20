package ir.co.sadad.promissory.dtos;

import lombok.Data;

@Data
public class GuaranteeApproveFinalResDto {
    private String guaranteeNN;
    private String guaranteeCellphone;
    private String guaranteeAccountNumber;
    private String guaranteeFullName;
    private String guaranteeAddress;
    private String paymentPlace;
    private String description;
    private String ownerNN;
    private String ownerFullName;
    private String promissoryId;
    private String confirmDate;
    private String requestId;
}
