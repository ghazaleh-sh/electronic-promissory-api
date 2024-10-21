package ir.co.sadad.promissory.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.co.sadad.promissory.commons.enums.CancelType;
import ir.co.sadad.promissory.commons.enums.RequestAction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartableResDto {
    private Long requestId;
    private String promissoryId;
    private String requestStatus;
    private String localizedRequestStatus;
    private String requesterValue;
    private String seconderValue;
    private BigDecimal promissoryAmount;
    private List<RequestAction> actions;
    private List<String> localizedActions;
    private String firstPersonName;
    private String secondPersonName;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private CancelType cancelType;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String localizedCancelType;

}
