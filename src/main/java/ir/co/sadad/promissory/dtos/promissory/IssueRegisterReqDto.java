package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IssueRegisterReqDto {
    /**
     * نوع شخص صادرکننده سفته
     * I حقیقی
     * C حقوقی
     */
    private String issuerType;
    /**
     * شماره ملی (10 رقم) / شناسه ملی(11 رقم)
     */
    private String issuerNN;
    /**
     * شماره همراه صادرکننده رشته به طول 10
     * - برای شخص حقیقی شماره همراه بدون صفر اولیه
     * - برای شخص حقوقی شماره تلفن ثابت شامل کد شهرستان بدون صفر
     */
    private String issuerCellphone;
    /**
     * نام کامل صادرکننده سفته
     */
    private String issuerFullName;
    /**
     * شماره شبا صادرکننده سفته
     */
    private String issuerAccountNumber;
    /**
     * آدرس صادرکننده سفته
     * طول رشته بین 5 تا 200 کاراکتر
     */
    private String issuerAddress;
    /**
     * کدپستی صادرکننده سفته
     * رشته به طول 10 کاراکتر
     */
    private String issuerPostalCode;
    /**
     * رشته به طول 10 کاراکتر
     * تاریخ شمسی و در قالب yyyyMMdd
     */
    private String issuerBirthdate;

    private String recipientType;
    private String recipientNN;
    private String recipientCellphone;
    private String recipientFullName;
    /**
     * محل پرداخت
     * طول رشته بین 5 تا 200 کاراکتر
     */
    private String paymentPlace;
    /**
     * مبلغ تعهد (ریال)
     * مبلغ سفته که صادرکننده متعهد به پرداخت آن میشود
     */
    private BigDecimal amount;
    /**
     * yyyyMMdd تاریخ سررسید، شمسی و در قالب
     */
    private String dueDate;
    /**
     * توضیحات طول رشته 200 کاراکتر
     */
    private String description;
    /**
     * این فیلد مشخص کننده قابل حواله کرد بودن استد
     */
    private Boolean transferable;
    /**
     * اطلاعات اختیاری ThirdParty
     */
    private Object extraData;
    private String guarantorsCount;
    private GuaranteeRequestsDto guaranteeRequests;

    @Data
    public static class GuaranteeRequestsDto {
        private String guaranteeType;
        private String guaranteeNN;
        private String guaranteeCellphone;
        private String guaranteeFullName;
        private String guaranteeAccountNumber;
        private String guaranteeAddress;
        private String paymentPlace;
        private String description;
        private String issuerType;
        /**
         * شماره/شناسه ملی صادرکننده سفته
         */
        private String nationalNumber;
        private Object extraData;
    }

}
