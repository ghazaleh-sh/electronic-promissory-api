package ir.co.sadad.promissory.commons.exceptions;

import ir.co.sadad.promissory.commons.exceptions.handler.dtos.GeneralSubErrorResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * main exception in project
 */
@Getter
public class PromissoryException extends RuntimeException {

    private final HttpStatusCode httpStatus;
    private Object[] parameters;
    private final List<GeneralSubErrorResponseDto> subErrors;
    private final String code;
    private final String jsonError;

    public PromissoryException(String message, HttpStatusCode httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.subErrors = null;
        this.code = null;
        this.jsonError = null;
    }

    public PromissoryException(String message,
                               HttpStatusCode httpStatus,
                               Object... descriptionParameters) {
        super(message);
        this.httpStatus = httpStatus;
        this.parameters = descriptionParameters;
        this.subErrors = null;
        this.code = null;
        this.jsonError = null;
    }

    public PromissoryException(String message,
                               HttpStatusCode httpStatus,
                               List<GeneralSubErrorResponseDto> subErrors) {
        super(message);
        this.subErrors = subErrors;
        this.httpStatus = httpStatus;
        this.code = null;
        this.jsonError = null;
    }

    public PromissoryException(String message,
                               String code,
                               HttpStatusCode httpStatus,
                               List<GeneralSubErrorResponseDto> subErrors,
                               String jsonError) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
        this.subErrors = subErrors;
        this.jsonError = jsonError;

    }

    public PromissoryException(String message, HttpStatusCode httpStatus, String code) {
        super(message);
        this.httpStatus = httpStatus;
        this.subErrors = null;
        this.jsonError = null;
        if (this.httpStatus.value() == UNAUTHORIZED.value()) {
            this.code = "PR401";
        } else this.code = code;
    }

    public PromissoryException(String message, HttpStatusCode httpStatus, String code, String jsonError) {
        super(message);
        this.httpStatus = httpStatus;
        this.subErrors = null;
        this.code = code;
        this.jsonError = jsonError;

    }
}
