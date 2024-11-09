package ir.co.sadad.promissory.dtos.promissory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * اطلاعات ظهرنویس ها
 */
@Data
public class EndorsementInfo {
    @JsonIgnore
    private String ownerType;
    private String ownerNN;
    private String ownerCellphone;
    private String ownerAccountNumber;
    private String ownerFullName;
    private String ownerAddress;
    @JsonIgnore
    private String recipientType;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    private String paymentPlace;
    private String description;
    private String creationDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String creationTime;
}
