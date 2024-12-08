package ir.co.sadad.promissory.dtos;

import lombok.Data;

@Data
public class IssueSecondResDto {

    private String promissoryId;
    private String issuerNN;
    private String issuerCellphone;
    private String issuerFullName;
    private String issuerAccountNumber;
    private String issuerAddress;
    private String issuerPostalCode;
    private String issuerBirthdate;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    private String paymentPlace;
    private Long amount;
    private String dueDate;
    private Boolean transferable;
    private Long fee;
    private Long stampDuty;
    private Long feeAndStampTotal;
    private String paymentDateTime;
    private String feeTransId;
    private String stampTransId;
    private String promissoryName;

}
