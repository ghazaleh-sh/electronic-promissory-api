/*
package ir.co.sadad.promissory.commons.exceptions;

import ir.co.sadad.promissory.dtos.baambase.BaambaseErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.List;

@Getter
public class BaambaseClientException extends RuntimeException {
    protected String message;
    protected String code;
    private final HttpStatusCode httpStatus;
    private final String localizedMessage;
    private final List<GeneralSubErrorResponseDto> subErrors;


    public List<BaambaseErrorResponseDto.SubErrorDto> extraData;

    public BaambaseClientException(String responseCode) {

//        super(Status.BAD_REQUEST);
        this.message = responseCode;
        this.code = responseCode;
    }

    public BaambaseClientException(String message, Status status) {

        super(status);
        this.message = message;
        this.code = String.valueOf(status.getStatusCode());
    }


    public BaambaseClientException(Status statusType, String code, List<BaambaseErrorResponseDto.SubErrorDto> extraData) {
        this.statusType = statusType;
        this.code = code;
        this.message = code;
        this.extraData = extraData;
    }

}
*/
