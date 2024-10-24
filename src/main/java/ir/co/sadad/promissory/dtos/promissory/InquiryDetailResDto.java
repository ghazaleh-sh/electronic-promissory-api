package ir.co.sadad.promissory.dtos.promissory;

import ir.co.sadad.promissory.commons.enums.PromissoryState;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InquiryDetailResDto {
    private String issuerType;
    private String issuerNN;
    private String issuerCellphone;
    private String issuerFullName;
    private String issuerAccountNumber;
    private String issuerAddress;
    private String issuerPostalCode;
    private String recipientType;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    private String paymentPlace;
    private BigDecimal amount;
    /**
     * مقدار باقیمانده تعهد)ریال(؛ به ازاي سفته هایی که در وضعیت تسویه
     * هستند برابر 0 ، در وضعیت تسویه تدریجی برابر با تفاضل مبلغ سفته و
     * مبالغ تسویه شده و به ازاي سایر وضعیت ها برابر با مبلغ تعهد است
     */
    private String remainingAmount;
    /**
     * تاریخ سررسید شمسی و در قالب yyyy/MM/dd
     */
    private String dueDate;
    private String description;
    /**
     * تاریخ صدور سفته و در قالب yyyy/MM/dd
     */
    private String creationDate;
    private String creationTime;
    private PromissoryState state;
    private String agentName;
    private String enAgentName;
    private Long paymentId;
    /**
     * شناسه 30 رقمی پرداخت (که در پاسخ درخواست صدور سفته در قالب
     * فیلد requestId بازگردانده می شود)
     */
    private String tpn;
    private Boolean transferable;
    private List<GuarantorInfo> guarantors;
    private Boolean isEndorsed;
    private List<EndorsementInfo> endorsements;
    private List<SettlementInfo> settlements;
    private Boolean isGuaranteed;
}
