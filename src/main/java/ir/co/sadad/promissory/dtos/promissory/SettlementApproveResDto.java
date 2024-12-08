package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class SettlementApproveResDto {

    private String ownerNN;
    private Long settlementAmount;
    private String promissoryId;
    private String requestDate;
    private String requestId;
}
