package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class GuaranteeListReqDto {
    /**
     * شناسه صادر کننده سفته
     */
    private String issuerNN;

    private String IssuerType;

    private String promissoryId;

}
