package ir.co.sadad.promissory.providers.promissory.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.promissory.PromissoryClientErrorResponseDto;
import ir.co.sadad.promissory.services.utils.DataConverter;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;

public class PromissoryClientErrorDecoderConfig implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        PromissoryClientErrorResponseDto responseBody = null;
        try {
            InputStream body = response.body().asInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            responseBody = objectMapper.readValue(body, PromissoryClientErrorResponseDto.class);
        } catch (IOException e) {
            return new PromissoryException("PROMISSORY_CLIENT_EXTERNAL_EXCEPTION", HttpStatus.valueOf(response.status()));
        }

//        if (responseBody.getDetailsInfo() != null && !responseBody.getDetailsInfo().isEmpty()) {
//
//            GeneralSubErrorResponseDto generalSubError = new GeneralSubErrorResponseDto();
//            List<GeneralSubErrorResponseDto> allSubErrors = new ArrayList<>();
//            responseBody.getDetailsInfo().forEach(sub -> {
//                generalSubError.setMessage(sub.get());
//                generalSubError.setCode(sub.getCode());
//                allSubErrors.add(generalSubError);
//            });
//            return new PromissoryException(PROMISSORY_CLIENT_GENERAL_MESSAGE_EXCEPTION, responseBody.getErrorCode(), HttpStatus.valueOf(response.status()), allSubErrors);
//        }
        return new PromissoryException(responseBody.getMessage() != null ? responseBody.getMessage() : "PROMISSORY_CLIENT_EXTERNAL_EXCEPTION",
                HttpStatus.valueOf(response.status()), responseBody.getResponseCode(), DataConverter.convertResponseToJson(responseBody));

    }
}
