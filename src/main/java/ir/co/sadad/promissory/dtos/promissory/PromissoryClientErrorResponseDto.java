package ir.co.sadad.promissory.dtos.promissory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * error response of promissory client services
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PromissoryClientErrorResponseDto implements ThirdPartyServicesResponse {
    private HttpStatus status;
    private String responseCode;
    private String timestamp;
    private String message;
    private String detail;
    private ErrorDetailDto detailsInfo;
    private Object extraData;

    @Data
    public static class ErrorDetailDto {
        /**
         * نام کارگزار درخواستکننده به انگلیسی
         */
        private String enClientName;
        /**
         * نام کارگزار درخواستکننده به فارسی
         */
        private String clientName;
        /**
         * شناسه یکتای 30 رقمی پرداخت
         */
        private String requestId;
    }
}
