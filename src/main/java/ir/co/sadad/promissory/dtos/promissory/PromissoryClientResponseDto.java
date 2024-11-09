package ir.co.sadad.promissory.dtos.promissory;

import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import lombok.Data;

/**
 * successful response of promissory client services
 */
@Data
public class PromissoryClientResponseDto<T> implements ThirdPartyServicesResponse {

    private String resMessage;
    private String resCode;
    private T Info;
    private Object extraData;
}
