package ir.co.sadad.promissory.dtos.baambase;

import lombok.Builder;
import lombok.Data;

@Data
public class DataToBeSignReqDto {

    private String document;
    private Parameters parameters;

    @Data
    @Builder
    public static class Parameters {
        private SigningCertificateObject signingCertificate;
        private String signatureAlgorithm;

        @Data
        @Builder
        public static class SigningCertificateObject {
            private String encodedCertificate;
        }
    }
}
