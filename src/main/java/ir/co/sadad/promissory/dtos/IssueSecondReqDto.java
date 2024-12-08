package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueSecondReqDto {

    @Schema(title = "شماره درخواست صدور سفته")
    @NotBlank(message = "V_REQUEST_ID_REQUIRE")
    private String requestId;

    @Schema(title = "pdf امضا شده توسط SDK")
    @NotBlank(message = "V_SIGNATURE_VALUE_REQUIRE")
    private String signatureValue;
}
