package ir.co.sadad.promissory.providers.baambase.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.commons.exceptions.handler.dtos.GeneralSubErrorResponseDto;
import ir.co.sadad.promissory.dtos.baambase.BaambaseErrorResponseDto;
import ir.co.sadad.promissory.services.utils.DataConverter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BaambaseClientErrorDecoderConfig implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        BaambaseErrorResponseDto responseBody = null;
        try {
            InputStream body = response.body().asInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            responseBody = objectMapper.readValue(body, BaambaseErrorResponseDto.class);
        } catch (IOException e) {
            return new PromissoryException("BAAMBASE_EXTERNAL_EXCEPTION", HttpStatus.valueOf(response.status()));
        }

        if (responseBody.getSubErrors() != null && !responseBody.getSubErrors().isEmpty()) {

            GeneralSubErrorResponseDto generalSubError = new GeneralSubErrorResponseDto();
            List<GeneralSubErrorResponseDto> allSubErrors = new ArrayList<>();
            responseBody.getSubErrors().forEach(sub -> {
                generalSubError.setMessage(sub.getMessage());
                generalSubError.setCode(sub.getCode());
                allSubErrors.add(generalSubError);
            });
            return new PromissoryException("BAAMBASE_EXTERNAL_EXCEPTION", responseBody.getErrorCode(), HttpStatus.valueOf(response.status()), allSubErrors,
                    DataConverter.convertResponseToJson(responseBody));
        }
        return new PromissoryException("BAAMBASE_EXTERNAL_EXCEPTION", HttpStatus.valueOf(response.status()), responseBody.getErrorCode(),
                DataConverter.convertResponseToJson(responseBody));

    }
}
