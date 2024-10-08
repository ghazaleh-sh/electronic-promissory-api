package ir.co.sadad.promissory.dtos.baambase;

import lombok.Data;

@Data
public class CertificationResBodyDto {

    private String certificateKeyId;
    private Integer productUid;
    private String productCName;
    private String deviceId;
    private String deviceName;
    private String name;
    private String subjectCN;
    private String subjectDN;
    private String issuerCN;
    private String startDate;
    private String endDate;
    private String lastSync;
    private String status;
    private String device;
    private String serialNumber;
    private String thumbPrint;
}
