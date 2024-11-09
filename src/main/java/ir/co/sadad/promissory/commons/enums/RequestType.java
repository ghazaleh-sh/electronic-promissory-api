package ir.co.sadad.promissory.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestType {

    ISSUE("P"),
    ENDORSEMENT("E"),
    GUARANTEE("G"),
    GRADUAL_SETTLEMENT("GR"),
    COMPLETE_SETTLEMENT("S");

    private final String docType;
}
