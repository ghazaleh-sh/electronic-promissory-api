package ir.co.sadad.promissory.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestAction {
    DETAIL_VIEW("جزئیات"),
    APPROVE("تایید درخواست"),
    CANCEL("لغو درخواست"),

    ADD_GUARANTOR("افزودن ضامن"),
//        SETTLEMENT("تسویه"),
    COMPLETE_SETTLEMENT("تسویه کامل"),
    GRADUAL_SETELMENT("تسویه تدریجی"),
    ENDORSEMENT("انتقال");

    private final String description;
}
