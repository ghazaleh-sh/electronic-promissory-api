package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DemandListResDto {
    /**
     * درخواستهای یافت شده منطبق با ورودی سرویس
     */
    private List<Item> items;
    /**
     * تعداد کل درخواستهای منطبق با ورودی سرویس
     */
    private int count;

    @Data
    public static class Item {
        private Long uid;
        private String promissoryId;
        private String state;
        private int requesterType;
        private String requesterValue;
        private int seconderType;
        private String seconderValue;
        private BigDecimal promissoryAmount;
        private List<String> actions;
        private String firstPersonName;
        private String secondPersonName;
    }
}
