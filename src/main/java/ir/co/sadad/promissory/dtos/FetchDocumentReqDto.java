package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import ir.co.sadad.promissory.commons.enums.RequestType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FetchDocumentReqDto {
    @Schema(title = "شماره/شناسه ملی گیرنده جدید در اثر ظهرنویسی/ ضامن")
    @NotBlank(message = "V_NATIONAL_NUMBER_REQUIRE")
    private String nationalNumber;

    @Schema(title = "نوع سند")
    @NotNull(message = "V_DOC_TYPE_REQUIRE")
    private RequestType docType;

    @Schema(title = "شناسه یکتای سفته")
    @NotBlank(message = "V_PROMISSORY_ID_REQUIRE")
    private String promissoryId;

    @Schema(title = "شماره تسویه تدریجی")
    private int number;
}
