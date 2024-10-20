package ir.co.sadad.promissory.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestStatus {
    REGISTERED("پیش نویس شده"),
    PRIMITIVE("ثبت اولیه"),
    //    APPROVED_WAITING_FOR_GUARANTOR("تایید شده در انتظار ضامن"),
    WAITING_FOR_GUARANTOR_APPROVED("در انتظار تایید و امضاء ضامن"),
    WAITING_FOR_GUARANTORS_APPROVED("در انتظار تایید و امضاء ضامنین"), //when guarantor just is pre-registered
    REGISTERED_WAITING_FOR_GUARANTOR_APPROVED("پیش نویس شده، در انتظار امضا ضامن"), //when promissory is signed by stakeholders except guarantor
    GUARANTOR_APPROVED("امضاء شده توسط ضامن"),// when guarantor signed and approved the promissory
    ENDORSEMENT_REGISTERED("پیش نویس ظهرنویسی"),
    ENDORSEMENT_APPROVED("ظهر نویسی نهایی شده"),
    COMPLETE_SETTLEMENT_REGISTERED("پیش نویش تسویه کامل"),
    COMPLETE_SETTLEMENT_APPROVED("تسویه کامل نهایی شده"),
    GRADUAL_SETTLEMENT_REGISTERED("پیش نویس تسویه تدریجی"),
    GRADUAL_SETTLEMENT_APPROVED("تسویه تدریجی نهایی شده"),
    FINALIZED("نهایی شده"),
    REJECTED("درخواست رد شده توسط ضامن"),
    CANCELED("درخواست حذف شده");

    private final String description;
}
