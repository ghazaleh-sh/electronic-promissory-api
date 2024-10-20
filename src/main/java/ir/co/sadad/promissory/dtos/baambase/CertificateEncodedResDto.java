package ir.co.sadad.promissory.dtos.baambase;

import lombok.Data;

@Data
public class CertificateEncodedResDto {
    private String certificateKeyId;
    private String username;
    private String certificatePemFile;
}
