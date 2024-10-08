package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class FetchSignedPdfResDto {
    private String multiSignedPdf;
    private int count;
}
