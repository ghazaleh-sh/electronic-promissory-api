package ir.co.sadad.promissory.dtos.promissory;

import lombok.Data;

@Data
public class FetchSignedPdfReqDto {
    private String nationalNumber;
    private String personType;
    private String docType;
    private String promissoryId;
    private int number;
}
