package ir.co.sadad.promissory.dtos;

import lombok.Data;

@Data
public class IssueAndGuaranteeAndSettlementRegisterResDto {
    private String requestId;
    private String certificateKeyId;
    private String device;
    private String deviceName;
    private String deviceId;
    private String productType;
    private int productId;
    private String dataToBeSign;

}
