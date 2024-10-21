package ir.co.sadad.promissory.mapper;

import ir.co.sadad.promissory.commons.enums.RequestAction;
import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.dtos.CartableResDto;
import ir.co.sadad.promissory.dtos.FetchDocumentReqDto;
import ir.co.sadad.promissory.dtos.InquiryResDto;
import ir.co.sadad.promissory.dtos.MyPromissoryResDto;
import ir.co.sadad.promissory.dtos.promissory.*;
import ir.co.sadad.promissory.services.utils.DataConverter;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PromissoryInfoMapper {

    @Mapping(source = "ssn", target = "identifier.value")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.cartableTypeConverter(identifier.getValue()))",
            target = "identifier.code")
    DemandListReqDto toDemandListReqDto(String ssn);

    CartableResDto toPerCartableResDto(DemandListResDto.Item cartableResItem);

    @IterableMapping(qualifiedByName = "ListMapper")
    List<CartableResDto> toCartableResDto(List<DemandListResDto.Item> cartableResItems);

    @Named("ListMapper")
    default CartableResDto cartableMap(DemandListResDto.Item item) {
        CartableResDto resDto = toPerCartableResDto(item);
        resDto.setRequestId(item.getUid());
        resDto.setRequestStatus(item.getState());
        resDto.setLocalizedRequestStatus(RequestStatus.valueOf(item.getState()).getDescription());

        resDto.setLocalizedActions(item.getActions().stream().
                map(action -> RequestAction.valueOf(action).getDescription()).toList());
//        List<String> localizedActionList = new ArrayList<>();
//        item.getActions().forEach(action -> localizedActionList.add(RequestAction.valueOf(action).getDescription()));
//        resDto.setLocalizedActions(localizedActionList);

        return resDto;
    }

    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.toPersianUtcDate(inquiryRes.getDueDate()))",
            target = "dueDate")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.toPersianUtcDateTime(inquiryRes.getCreationDate(), inquiryRes.getCreationTime()))",
            target = "creationDate")
    @Mapping(expression = "java(inquiryRes.getState().getState())", target = "state")
    @Mapping(expression = "java(inquiryRes.getState().getLocalizedState())", target = "localizedState")
    @Mapping(source = "tpn", target = "requestId")
    @Mapping(source = "guarantors", target = "guarantors", qualifiedByName = "guarantorsDateTime")
    @Mapping(source = "endorsements", target = "endorsements", qualifiedByName = "endorsementsDateTime")
    @Mapping(source = "settlements", target = "settlements", qualifiedByName = "settlementsDateTime")
    InquiryResDto toInquiryResDto(InquiryDetailResDto inquiryRes);

    @Named("guarantorsDateTime")
    default List<GuarantorInfo> guarantorsDateTime(List<GuarantorInfo> guarantors) {
        guarantors.forEach(guarantor -> {
            guarantor.setCreationDate(DataConverter
                    .toPersianUtcDateTime(guarantor.getCreationDate(), guarantor.getCreationTime()));
        });

        return guarantors;
    }

    @Named("endorsementsDateTime")
    default List<EndorsementInfo> endorsementsDateTime(List<EndorsementInfo> endorsments) {
        endorsments.forEach(endorsement -> {
            endorsement.setCreationDate(DataConverter
                    .toPersianUtcDateTime(endorsement.getCreationDate(), endorsement.getCreationTime()));
        });

        return endorsments;
    }

    @Named("settlementsDateTime")
    default List<SettlementInfo> settlementsDateTime(List<SettlementInfo> settlements) {
        settlements.forEach(settlement -> {
            settlement.setCreationDate(DataConverter
                    .toPersianUtcDateTime(settlement.getCreationDate(), settlement.getCreationTime()));
        });

        return settlements;
    }

    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.personTypeConverter(documentReqDto.getNationalNumber()))",
            target = "personType")
    @Mapping(expression = "java(documentReqDto.getDocType().getDocType())", target = "docType")
    FetchSignedPdfReqDto toFetchSignedReqDto(FetchDocumentReqDto documentReqDto);


    @IterableMapping(qualifiedByName = "MyPromissoryListMapper")
    List<MyPromissoryResDto> toMyPromissoryListResDto(List<MyPromissoryClientResDto.MyPromissoryList> list);

    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.toPersianUtcDate(eachPromissory.getDueDate()))",
            target = "dueDate")
    @Mapping(expression = "java(ir.co.sadad.promissory.services.utils.DataConverter.toPersianUtcDateTime(eachPromissory.getCreationDate(), eachPromissory.getCreationTime()))",
            target = "creationDate")
    MyPromissoryResDto toMyPromissoryResDto(MyPromissoryClientResDto.PromissoryObject eachPromissory);

    @Named("MyPromissoryListMapper")
    default MyPromissoryResDto myPromissoryMap(MyPromissoryClientResDto.MyPromissoryList eachItem) {
        MyPromissoryResDto resDto = toMyPromissoryResDto(eachItem.getPromissory());
        resDto.setRole(StakeholderRole.valueOf(eachItem.getPromissory().getRole()));
        resDto.setState(eachItem.getPromissory().getState().getState());
        resDto.setLocalizedState(eachItem.getPromissory().getState().getLocalizedState());
        resDto.setLocalizedRequestStatus(eachItem.getPromissory().getRequestStatus().getDescription());

        resDto.setAction(eachItem.getActionList().stream().map(RequestAction::valueOf).toList());
        resDto.setLocalizedAction(eachItem.getActionList().stream().
                map(action -> RequestAction.valueOf(action).getDescription()).toList());

        return resDto;
    }

}
