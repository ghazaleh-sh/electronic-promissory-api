package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GuaranteeListResDto {

    private List<DemandList> demandList;
    private List<GuarantorInfo> approvedGuarantors;
    private int count;

    @Data
    public static class DemandList {
        private Long uid;
        private String promissoryId;
        private String state;//enum
        @JsonIgnore
        private String requesterType;
        private String requesterValue;
        @JsonIgnore
        private String seconderType;
        private String seconderValue;
        private BigDecimal promissoryAmount;
        private String demandType; //enum
        private List<String> actions;
        private String firstPersonName;
        private String secondPersonName;
    }

}
