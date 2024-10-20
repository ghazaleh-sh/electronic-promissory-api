package ir.co.sadad.promissory.dtos.icms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ICMSErrorResponseDto implements ThirdPartyServicesResponse {

    private SubErrorDto error;
    private String response;

    @Data
    public static class SubErrorDto {
        private String code;
        private String message;
        private String timestamp;
        private String domain;
        private List<Object> errors;
    }
}
