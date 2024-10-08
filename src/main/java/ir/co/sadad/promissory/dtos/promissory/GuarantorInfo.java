package ir.co.sadad.promissory.dtos.promissory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * اطلاعات ضامن ها
 */
@Data
public class GuarantorInfo {
    @JsonIgnore
    private String guaranteeType;
    private String guaranteeNN;
    private String guaranteeCellphone;
    private String guaranteeAccountNumber;
    private String guaranteeFullName;
    private String guaranteeAddress;
    private String paymentPlace;
    private String description;
    private String creationDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String creationTime;
}
