package ir.co.sadad.promissory.dtos.promissory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SettlementRegisterReqDto {
//    @Schema(title = "شماره ملی صادر کننده سفته")
//    @NotBlank(message = "V_ISSUER_NN_REQUIRE")
    private String ownerNN;

    @Schema(title = "مبلغ تسویه")
    @NotBlank(message = "V_REQUEST_SETTLEMENT_AMOUNT_REQUIRE")
    private Long settlementAmount;

    @Schema(title = "شناسه یکتای 16 رقمی سفته")
    @NotBlank(message = "V_PROMISSORY_ID_REQUIRE")
    private String promissoryId;
}
