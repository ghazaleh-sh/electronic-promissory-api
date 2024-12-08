package ir.co.sadad.promissory.dtos;

import lombok.Data;

@Data
public class UserCertificationResDto {
    private String certificateKeyId;
    private String device;
    private String deviceId;
    private String deviceName;
    private String productType;
    private Integer productId;
    private String status;
}
