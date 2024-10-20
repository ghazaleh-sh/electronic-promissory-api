package ir.co.sadad.promissory.dtos;

import ir.co.sadad.promissory.commons.enums.RequestAction;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MyPromissoryResDto {
    private StakeholderRole role;
    private String promissoryId;
    private String issuerNN;
    private String issuerCellphone;
    private String issuerFullName;
    private String issuerAccountNumber;
    private String issuerAddress;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    private String paymentPlace;
    private BigDecimal amount;
    private String remainingAmount;
    private String dueDate;
    private String description;
    private Long paymentId;
    private String creationDate;
    private String state;
    private String localizedState;
    private Boolean transferable;
    private String agentName;
    private String enAgentName;
    private Boolean isEndorsed;
    private Boolean isGuaranteed;
    private RequestStatus requestStatus;
    private String localizedRequestStatus;
    private String PromissoryName;
    private List<RequestAction> action;
    private List<String> localizedAction;

    private int guarantorCount;
}
