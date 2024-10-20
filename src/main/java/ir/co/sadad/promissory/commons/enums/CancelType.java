package ir.co.sadad.promissory.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CancelType {
    DELETE("حذف"),
    REJECT("عدم تایید");

    private final String description;
}
