package ir.co.sadad.promissory.services;

import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.dtos.CartableResDto;
import ir.co.sadad.promissory.dtos.FetchDocumentReqDto;
import ir.co.sadad.promissory.dtos.InquiryResDto;
import ir.co.sadad.promissory.dtos.MyPromissoryResDto;
import ir.co.sadad.promissory.dtos.promissory.FetchSignedPdfResDto;
import ir.co.sadad.promissory.dtos.promissory.InquiryReqDto;

import java.util.List;

public interface PromissoryInfoService {

    List<CartableResDto> cartable(String ssn, int page_number, int page_size);

    InquiryResDto inquiry(InquiryReqDto inquiryReqDto);

    InquiryResDto inquiryDetails(String ssn, String promissoryId);

    FetchSignedPdfResDto fetchSignedPdf(FetchDocumentReqDto documentReqDto);

    List<MyPromissoryResDto> myPromissory(String ssn, int page_number, int page_size, StakeholderRole role);
}
