package ir.co.sadad.promissory.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestAction {
    DETAIL_VIEW("جزئیات"),
    APPROVE("تایید درخواست"),
    CANCEL("لغو درخواست"),

    ADD_GUARANTEE("افزودن ضامن"),
    SETTLEMENT("تسویه"),
    ENDORSEMENT("انتقال");

    private final String description;
}
