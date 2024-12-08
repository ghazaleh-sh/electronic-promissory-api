package ir.co.sadad.promissory.commons.exceptions.handler.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * handle sub errors
 */
@NoArgsConstructor
@Data
public class GeneralSubErrorResponseDto {
    private String code;
    private String message;
    private String localizedMessage;
}
