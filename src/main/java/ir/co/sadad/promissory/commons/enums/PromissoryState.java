package ir.co.sadad.promissory.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PromissoryState {
    F("FINAL_ISSUE", "صدور"),
    G("GRADUAL_SETTLEMENT", "تسویه تدریجی"),
    S("COMPLETE_SETTLEMENT", "تسویه کامل"),
    D("DEPOSIT", "واخواست"),
    R("REVOKE", "ابطال");

    private final String state;
    private final String localizedState;
}
