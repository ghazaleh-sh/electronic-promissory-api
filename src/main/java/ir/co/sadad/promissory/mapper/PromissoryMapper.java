package ir.co.sadad.promissory.mapper;

import ir.co.sadad.promissory.dtos.*;
import ir.co.sadad.promissory.dtos.baambase.CertificationResBodyDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteePreRegisterReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeRegisterReqDto;
import ir.co.sadad.promissory.dtos.promissory.IssueRegisterReqDto;
import ir.co.sadad.promissory.entities.Promissory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PromissoryMapper {

    @Mapping(target = "productType", expression = "java(ir.co.sadad.promissory.commons.Constants.PRODUCT_TYPE)")
    @Mapping(source = "productUid", target = "productId")
    UserCertificationResDto toUserCertificationResDto(CertificationResBodyDto certList);

    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(registerReqDto.getIssuerNN()))",
            target = "issuerType")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(registerReqDto.getRecipientNN()))",
            target = "recipientType")
    @Mapping(expression = "java(ir.co.sadad.hambaam.persiandatetime.PersianUTC.fromUTC(registerReqDto.getDueDate()).getDate(java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd\")))",
            target = "dueDate")
    @Mapping(expression = "java(ir.co.sadad.hambaam.persiandatetime.PersianUTC.fromUTC(registerReqDto.getIssuerBirthdate()).getDate(java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd\")))",
            target = "issuerBirthdate")
    @Mapping(source = "promissoryName", target = "extraData", qualifiedByName = "stringToObject")
    @Mapping(target = "issuerAddress", expression = "java(registerReqDto.getIssuerAddress() == null ? registerReqDto.getPaymentPlace() : registerReqDto.getIssuerAddress())")
    @Mapping(target = "transferable", expression = "java(registerReqDto.getTransferable() == null ? true : registerReqDto.getTransferable())")
    IssueRegisterReqDto toIssueRegisterReqDto(IssueFirstReqDto registerReqDto);

    @Named("stringToObject")
    default Object stringToObject(String stringName) {
        return stringName;
    }

    @Mapping(source = "promissoryUid", target = "promissoryId")
    IssueSecondResDto toIssueApproveDto(Promissory savedPromissory);


    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(ssn))",
            target = "issuerType")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(addGuaranteeReqDto.getGuaranteeNN()))",
            target = "guaranteeType")
    @Mapping(source = "ssn", target = "nationalNumber")
    GuaranteePreRegisterReqDto toPreregisterReqDto(String ssn, AddGuaranteeReqDto addGuaranteeReqDto);

    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(registerReqDto.getOwnerNN()))",
            target = "issuerType")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(ssn))",
            target = "guaranteeType")
    @Mapping(source = "ssn", target = "guaranteeNN")
    @Mapping(source = "registerReqDto.ownerNN", target = "nationalNumber")
    GuaranteeRegisterReqDto toRegisterReqDto(String ssn, AddGuaranteeRegisterReqDto registerReqDto);
}
