package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuaranteeDeleteOrRejectReqDto {

    @Schema(title = "شناسه درخواست")
    @NotNull(message = "V_REQUEST_ID_REQUIRE")
    private String uid; // == preGuaranteeRequestId

    @Schema(title = "نوع درخواست: حذف یا هدم تایید")
    @NotNull(message = "V_CANCEL_TYPE_REQUIRE")
    @Pattern(regexp = "^(CANCELED|REJECTED)$", message = "{V_CANCEL_TYPE_NOT_VALID}")
    private String cancelType;
}
