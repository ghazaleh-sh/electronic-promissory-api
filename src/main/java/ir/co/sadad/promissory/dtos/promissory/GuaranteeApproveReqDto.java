package ir.co.sadad.promissory.dtos.promissory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuaranteeApproveReqDto {

    @Schema(title = "شماره درخواست")
    @NotBlank(message = "V_REQUEST_ID_REQUIRE")
    private String requestId;

    /**
     * رشته PDF امضا شده سفته توسط کارگزار
     * و ذینفعان در قالب base64
     */
    @Schema(title = "pdf امضا شده توسط SDK")
    @NotBlank(message = "V_SIGNATURE_VALUE_REQUIRE")
    private String signedPdf;

    private Object extraData;
}
