package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class DemandListReqDto {
    private Identifier identifier;
    private Object extraData;

    @Data
    public static class Identifier {
        /**
         * کد نوع شخص
         * 1 : مشتری حقیقی
         * 3 : مشتری حقوقی
         */
        private int code;
        /**
         * شماره / شناسه ملی
         */
        private String value;
    }
}
