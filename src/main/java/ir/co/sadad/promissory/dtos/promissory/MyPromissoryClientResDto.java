package ir.co.sadad.promissory.dtos.promissory;

import ir.co.sadad.promissory.commons.enums.PromissoryState;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * اطلاعات دریافت شده از سرویس سفته های من از سمت کلاینت
 */
@Data
public class MyPromissoryClientResDto {
    private List<MyPromissoryList> list;
    private int count;

    @Data
    public static class MyPromissoryList {
        private String role;
        private String promissoryId;
        private String issuerType;
        private String issuerNN;
        private String issuerCellphone;
        private String issuerFullName;
        private String issuerAccountNumber;
        private String issuerAddress;
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
        private Long paymentId;
        private String creationDate;
        private String creationTime;
        private PromissoryState state;
        private Boolean transferable;
        private String agentName;
        private String enAgentName;
        private Boolean isEndorsed;
        private Boolean isGuaranteed;
        private RequestStatus requestStatus;
        private Object extraData;
    }
}
