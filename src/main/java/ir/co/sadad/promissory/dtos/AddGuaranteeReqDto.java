package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddGuaranteeReqDto {

    @Schema(title = "شماره ملی ضامن سفته")
    @NotBlank(message = "V_GUARANTOR_NN_REQUIRE")
    private String guaranteeNN;

    @Schema(title = "شناسه یکتای سفته")
    @NotBlank(message = "V_PROMISSORY_ID_REQUIRE")
    private String promissoryId;
}
