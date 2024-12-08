package ir.co.sadad.promissory.dtos.baambase;

import ir.co.sadad.promissory.dtos.ThirdPartyServicesResponse;
import lombok.Data;

/**
 * response of baambase userCertification service
 */
@Data
public class BaamBaseResponseDto<T> implements ThirdPartyServicesResponse {

    private String message;
    private T responseBody;
    private Integer resultCode;

}
