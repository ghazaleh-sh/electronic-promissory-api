package ir.co.sadad.promissory.dtos.icms;

import lombok.Data;

import java.util.List;

@Data
public class LegalCustomerInfoDto {
    private String nationalId;
    private String name;
    private String birthDate;
    private DetailInfo idCountry;
    private DetailInfo idCity;
    private String economicId;
    private DetailInfo lifeStatus;
    private List<CustomerInfoList> customerInfoList;
    private String shahabCode;
    private String latinName;
    private String fiscalYear;
    private String registerId;
    private String establishDate;
    private String officialNewspaperNo;
    private DetailInfo legalStructure;
    private DetailInfo lawType;
    private DetailInfo sanctionType;
    private String deathDate;
    private String referenceVerifiedDate;
    private String registeredCapital;
    private String lastChangeDate;

    @Data
    public static class DetailInfo {
        private String Code;
        private String title;
    }

    @Data
    public static class CustomerInfoList {
        private String customerNo;
        private String customerDefinitionDate;
        private String customerService;
        private DetailInfo customerDefinitionBranch;
    }

}
