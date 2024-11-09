package ir.co.sadad.promissory.providers.icms.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.icms.ICMSErrorResponseDto;
import ir.co.sadad.promissory.services.utils.DataConverter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class ICMSClientErrorDecoderConfig implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ICMSErrorResponseDto responseBody = null;
        try {
            InputStream body = response.body().asInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            responseBody = objectMapper.readValue(body, ICMSErrorResponseDto.class);
        } catch (IOException e) {
            return new PromissoryException("ICMS_CLIENT_EXTERNAL_EXCEPTION", HttpStatus.valueOf(response.status()));
        }

        return new PromissoryException(responseBody.getError() != null ? responseBody.getError().getMessage() : "ICMS_CLIENT_EXTERNAL_EXCEPTION",
                HttpStatus.valueOf(response.status()), responseBody.getError() != null ? responseBody.getError().getCode() : "", DataConverter.convertResponseToJson(responseBody));

    }
}
