package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddGuaranteeRegisterReqDto {
    @Schema(title = "شماره تلفن ضامن سفته")
    @NotBlank(message = "V_GUARANTOR_CELLPHONE_REQUIRE")
    private String guaranteeCellphone;

    @Schema(title = "شماره شبا ضامن سفته")
    @NotBlank(message = "V_GUARANTOR_ACCOUNT_NUMBER_REQUIRE")
    private String guaranteeAccountNumber;

    @Schema(title = "نام کامل ضامن سفته")
    @NotBlank(message = "V_GUARANTOR_FULL_NAME_REQUIRE")
    private String guaranteeFullName;

    @Schema(title = "آدرس ضامن سفته")
    @NotBlank(message = "V_GUARANTOR_ADDRESS_REQUIRE")
    private String guaranteeAddress;

    @Schema(title = "محل پرداخت")
    @NotBlank(message = "V_PAYMENT_PLACE_REQUIRE")
    private String paymentPlace;

    @Schema(title = "توضیحات")
    private String description;

    @Schema(title = "شماره ملی صادر کننده سفته")
    @NotBlank(message = "V_ISSUER_NN_REQUIRE")
    private String ownerNN;

    @Schema(title = "شناسه یکتای سفته")
    @NotBlank(message = "V_PROMISSORY_ID_REQUIRE")
    private String promissoryId;

    @Schema(title = "شناسه درخواست اولیه")
    @NotBlank(message = "V_REQUEST_ID_REQUIRE")
    private String uid;
}
