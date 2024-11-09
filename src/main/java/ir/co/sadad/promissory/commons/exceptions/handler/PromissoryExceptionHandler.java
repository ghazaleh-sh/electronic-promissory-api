package ir.co.sadad.promissory.commons.exceptions.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.timeout.TimeoutException;
import ir.co.sadad.hambaam.persiandatetime.PersianUTC;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.commons.exceptions.handler.dtos.GeneralErrorResponseDto;
import ir.co.sadad.promissory.commons.exceptions.handler.dtos.GeneralSubErrorResponseDto;
import ir.co.sadad.promissory.commons.exceptions.handler.dtos.HttpClientErrorDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class PromissoryExceptionHandler {
    private static final String notTranslatedMessage = "NOT_TRANSLATED_MESSAGE";
    private final MessageSource messageSource;

//    @ExceptionHandler(PreconditionFailedException.class)
//    public ResponseEntity<GeneralErrorResponseDto> handlePreconditionFailedException(PreconditionFailedException ex) {
//
//        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
//        generalErrorResponseDto
//                .setStatus(HttpStatusCode.valueOf(412))
//                .setTimestamp(PersianUTC.currentUTC().toString())
//                .setCode("412")
//                .setLocalizedMessage(messageSource.getMessage(ex.getMessage(), null, Locale.of("fa")))
//                .setMessage(ex.getMessage())
//                .setExtraData(ex.getExtraData());
//        return new ResponseEntity<>(generalErrorResponseDto, HttpStatusCode.valueOf(412));
//    }

    @ExceptionHandler(PromissoryException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleCoreServiceException(PromissoryException ex) {

        String localizedMessage;
        String message;
        try {
            localizedMessage = messageSource.getMessage(ex.getCode() != null ? ex.getCode() : ex.getMessage(),
                    ex.getParameters(), Locale.of("fa"));

        } catch (NoSuchMessageException e) {
            localizedMessage = messageSource.getMessage("GENERAL_UNKNOWN_ERROR", null, Locale.of("fa"));
        }

        try {
            message = messageSource.getMessage(ex.getCode(), ex.getParameters(), Locale.of("en"));
        } catch (NoSuchMessageException e) {
            message = ex.getMessage() != null ? ex.getMessage() : notTranslatedMessage;

        }
        if (ex.getSubErrors() != null) {
            ex.getSubErrors().forEach(sub ->
            {
                try {
                    sub.setLocalizedMessage(messageSource.getMessage(sub.getMessage(), null, Locale.of("fa")));
                } catch (NoSuchMessageException e) {
                    sub.setLocalizedMessage(null);
                }
            });
        }


        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(ex.getHttpStatus())
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode(ex.getCode() != null ? ex.getCode() : "PR-" + ex.getHttpStatus().value())
                .setLocalizedMessage(localizedMessage)
                .setMessage(message)
                .setSubErrors(ex.getSubErrors());

        return new ResponseEntity<>(generalErrorResponseDto, ex.getHttpStatus());

    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<GeneralErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex) {
        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(BAD_REQUEST)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + BAD_REQUEST.value())
                .setMessage("BUSINESS_ARGUMENT_NOT_VALID")
                .setLocalizedMessage(messageSource.getMessage("BUSINESS_ARGUMENT_NOT_VALID", null, Locale.of("fa")));


        List<GeneralSubErrorResponseDto> subErrorList = new ArrayList<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            GeneralSubErrorResponseDto subError = new GeneralSubErrorResponseDto();
            subError.setCode(Objects.requireNonNull(constraintViolation.getMessage()));
            try {
                subError.setLocalizedMessage(messageSource.getMessage(Objects.requireNonNull(constraintViolation.getMessage()),
                        null, Locale.of("fa")));
            } catch (NoSuchMessageException exp) {
                subError.setLocalizedMessage(constraintViolation.getMessage());
            }
            subError.setMessage(Objects.requireNonNull(constraintViolation.getMessage()));
            subErrorList.add(subError);
        });
        generalErrorResponseDto.setSubErrors(subErrorList);

        return new ResponseEntity<>(generalErrorResponseDto, BAD_REQUEST);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleConnectException(ConnectException ex) {
        log.error("Connect Exception Exception: ", ex);

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.REQUEST_TIMEOUT)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + HttpStatus.REQUEST_TIMEOUT.value())
                .setLocalizedMessage(messageSource.getMessage("GENERAL_CORE_SERVICE_TIMEOUT", null, Locale.of("fa")))
                .setMessage("GENERAL_CORE_SERVICE_TIMEOUT");

        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.REQUEST_TIMEOUT);

    }

    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleTimeOutException(TimeoutException ex) {
        log.warn("Connection Timeout Exception: ", ex);

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.REQUEST_TIMEOUT)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + HttpStatus.REQUEST_TIMEOUT.value())
                .setLocalizedMessage(messageSource.getMessage("GENERAL_CORE_SERVICE_TIMEOUT", null, Locale.of("fa")))
                .setMessage("GENERAL_CORE_SERVICE_TIMEOUT");

        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.REQUEST_TIMEOUT);

    }

    @ExceptionHandler(JDBCConnectionException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleJDBCConnectionException(JDBCConnectionException ex) {
        log.warn("JDBC Connection Exception: ", ex);

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + HttpStatus.INTERNAL_SERVER_ERROR)
                .setLocalizedMessage(messageSource.getMessage("GENERAL_DB_CONNECTION", null, Locale.of("fa")))
                .setMessage("GENERAL_DB_CONNECTION");

        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("api calling exception", ex);

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.BAD_REQUEST)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + BAD_REQUEST.value())
                .setLocalizedMessage(messageSource.getMessage("GENERAL_HTTP_MESSAGE_NOT_READABLE", null, Locale.of("fa")))
                .setMessage("GENERAL_HTTP_MESSAGE_NOT_READABLE");

        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        log.warn("validation exception", ex);
        String generalMsg = messageSource.getMessage("BUSINESS_ARGUMENT_NOT_VALID", null, Locale.of("fa"));

        List<GeneralSubErrorResponseDto> subErrorList = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            GeneralSubErrorResponseDto subError = new GeneralSubErrorResponseDto();
            subError.setCode(Objects.requireNonNull(error.getDefaultMessage()));
            try {
                subError.setLocalizedMessage(messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, Locale.of("fa")));
            } catch (NoSuchMessageException exp) {
                subError.setLocalizedMessage(error.getDefaultMessage());
            }
            subError.setMessage(Objects.requireNonNull(error.getDefaultMessage()));
            subErrorList.add(subError);
        });

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.BAD_REQUEST)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("PR-" + BAD_REQUEST.value())
                .setMessage("BUSINESS_ARGUMENT_NOT_VALID")
                .setLocalizedMessage(generalMsg)
                .setSubErrors(subErrorList);
        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.BAD_REQUEST);

    }

    /**
     * this exception happens when Db search over records of table that has No parent
     *
     * @param ex exception for not found entity
     * @return response of Exception
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.warn("Entity Not Found Exception ====>", ex);

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(HttpStatus.NOT_FOUND)
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + HttpStatus.NOT_FOUND.value())
                .setMessage("GENERAL_DATA_NOT_IN_DB")
                .setLocalizedMessage(messageSource.getMessage("GENERAL_DATA_NOT_IN_DB", null, Locale.of("fa")));
        return new ResponseEntity<>(generalErrorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<GeneralErrorResponseDto> handleHttpClientErrorException(
            HttpClientErrorException ex) throws JsonProcessingException {

        HttpClientErrorDto httpClientErrorDto = new ObjectMapper().readValue(ex.getResponseBodyAsString(), HttpClientErrorDto.class);

        List<GeneralSubErrorResponseDto> subErrorList = new ArrayList<>();
        httpClientErrorDto.getErrors().forEach((error) -> {
            GeneralSubErrorResponseDto subError = new GeneralSubErrorResponseDto();
            subError.setCode(error.getField() + " " + error.getDefaultMessage());
            subError.setMessage(notTranslatedMessage);
            subError.setLocalizedMessage(error.getField() + " " + error.getDefaultMessage());
            subErrorList.add(subError);
        });

        GeneralErrorResponseDto generalErrorResponseDto = new GeneralErrorResponseDto();
        generalErrorResponseDto
                .setStatus(ex.getStatusCode())
                .setTimestamp(PersianUTC.currentUTC().toString())
                .setCode("IA-" + ex.getStatusCode().value())
                .setLocalizedMessage(messageSource.getMessage("BUSINESS_ARGUMENT_NOT_VALID",
                        null,
                        Locale.of("fa")))
                .setMessage("BUSINESS_ARGUMENT_NOT_VALID")
                .setSubErrors(subErrorList);
        return new ResponseEntity<>(generalErrorResponseDto, ex.getStatusCode());

    }
}