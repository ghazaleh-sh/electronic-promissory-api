package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class GuaranteeApproveResDto {
    /**
     * رشته PDF امضا شده توسط کارگزار، ذینفعان و وزارت اقتصاد در قالب base64
     */
    private String multiSignedPdf;
}
