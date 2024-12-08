package ir.co.sadad.promissory.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(title = "درخواست صدور سفته")
public class IssueFirstReqDto {
    private String issuerNN;

    @Schema(title = "شماره همراه صادرکننده رشته به طول 10")
    @NotBlank(message = "V_ISSUER_CELLPHONE_REQUIRE")
    private String issuerCellphone;

    @Schema(title = "نام کامل صادرکننده سفته")
    @NotBlank(message = "V_ISSUER_FULL_NAME_REQUIRE")
    private String issuerFullName;

    @Schema(title = "شماره شبا صادرکننده سفته")
    @NotBlank(message = "V_ISSUER_ACCOUNT_NUMBER_REQUIRE")
    private String issuerAccountNumber;

    @Schema(title = "آدرس صادرکننده سفته")
    private String issuerAddress;

    @Schema(title = "کدپستی صادرکننده سفته")
    private String issuerPostalCode;

    @Schema(title = "تاریخ تولد صادرکننده سفته-utc")
    @NotBlank(message = "V_ISSUER_BIRTHDAY_REQUIRE")
    private String issuerBirthdate;

    @Schema(title = "شماره ملی دریافت کننده سفته")
    @NotBlank(message = "V_RECIPIENT_NN_REQUIRE")
    private String recipientNN;

    @Schema(title = "شماره همراه دریافت کننده سفته به طول 10")
    @NotBlank(message = "V_RECIPIENT_CELLPHONE_REQUIRE")
    private String recipientCellphone;

    @Schema(title = "نام کامل دریافت کننده سفته")
    @NotBlank(message = "V_RECIPIENT_FULL_NAME_REQUIRE")
    private String recipientFullName;

    @Schema(title = "محل پرداخت")
    @NotBlank(message = "V_PAYMENT_PLACE_REQUIRE")
    private String paymentPlace;

    @Schema(title = "مبلغ سفته که صادرکننده متعهد به پرداخت آن میشود(ریال)")
    @NotNull(message = "V_ISSUE_AMOUNT_REQUIRE")
    private Long amount;

    @Schema(title = "تاریخ سررسید سفته-utc")
    private String dueDate;

    @Schema(title = "قابل حواله بودن")
    private Boolean transferable;

    @Schema(title = "عنوان سفته")
    private String promissoryName;

}
