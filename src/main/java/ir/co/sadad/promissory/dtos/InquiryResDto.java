package ir.co.sadad.promissory.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.co.sadad.promissory.dtos.promissory.EndorsementInfo;
import ir.co.sadad.promissory.dtos.promissory.GuarantorInfo;
import ir.co.sadad.promissory.dtos.promissory.SettlementInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InquiryResDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String promissoryName;
    private String issuerNN;
    private String issuerCellphone;
    private String issuerFullName;
    private String issuerAccountNumber;
    private String issuerAddress;
    private String issuerPostalCode;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    private String paymentPlace;
    private BigDecimal amount;
    private String remainingAmount;
    private String dueDate;
    private String description;
    private String creationDate;
    private String state;
    private String localizedState;
    private String agentName;
    private String enAgentName;
    private Long paymentId;
    private String requestId;
    private Boolean transferable;
    private List<GuarantorInfo> guarantors;
    private Boolean isEndorsed;
    private List<EndorsementInfo> endorsements;
    private List<SettlementInfo> settlements;
    private Boolean isGuaranteed;
}
