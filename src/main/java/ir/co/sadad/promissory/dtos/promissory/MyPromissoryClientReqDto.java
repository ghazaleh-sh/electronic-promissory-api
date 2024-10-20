package ir.co.sadad.promissory.dtos.promissory;

import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import lombok.Builder;
import lombok.Data;

/**
 * اطلاعات جهت ارسال به سرویس سفته های من کلاینت
 */
@Data
@Builder
public class MyPromissoryClientReqDto {
    private String nationalNumber;
    private StakeholderRole role;
    private int page;
    private int size;
}
