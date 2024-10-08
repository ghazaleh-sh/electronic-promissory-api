package ir.co.sadad.promissory.dtos.promissory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquiryReqDto {
    @Schema(title = "شماره/شناسه ملی صادرکننده")
    @NotBlank(message = "V_ISSUER_NN_REQUIRE")
    private String issuerNN;

    @Schema(title = "شناسه بکتای سفته")
    @NotBlank(message = "V_PROMISSORY_ID_REQUIRE")
    private String promissoryId;
}
