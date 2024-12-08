package ir.co.sadad.promissory.dtos.baambase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import lombok.Data;

import java.util.List;

/**
 * error response of Baambase services
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaambaseErrorResponseDto implements ThirdPartyServicesResponse {
    private String errorCode;
    private String errorDesc;
    private List<SubErrorDto> subErrors;

    @Data
    public static class SubErrorDto {
        private String code;
        private String message;
    }

}
