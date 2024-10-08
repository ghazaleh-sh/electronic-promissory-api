package ir.co.sadad.promissory.services.daos;

import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.commons.exceptions.PromissoryException;
import ir.co.sadad.promissory.dtos.IssueFirstReqDto;
import ir.co.sadad.promissory.dtos.promissory.GuaranteeRegisterReqDto;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.entities.PromissoryStakeholder;
import ir.co.sadad.promissory.repositories.PromissoryStakeholderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing promissory stakeholders
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class PromissoryStakeholderDaoService {
    private final PromissoryStakeholderRepository stakeholderRepository;


    public List<PromissoryStakeholder> getStakeholders(PromissoryRequest request) {
        return stakeholderRepository.findByRequest(request);
    }

    public PromissoryStakeholder getStakeholderByRole(PromissoryRequest request, StakeholderRole role) {
        return stakeholderRepository.findByRequestAndRole(request, role).orElseThrow(() -> new PromissoryException("NO_STAKEHOLDER_FOUND", HttpStatus.BAD_REQUEST));
    }


    @Transactional(propagation = Propagation.NESTED)
    public void createStakeholdersForIssue(IssueFirstReqDto issueReqDto, PromissoryRequest request) {
        List<PromissoryStakeholder> stakeholders = new ArrayList<>();
        PromissoryStakeholder issuerStakeholder = getIssuerStakeholder(issueReqDto, request);
        stakeholders.add(issuerStakeholder);

        PromissoryStakeholder recipientStakeholder = new PromissoryStakeholder();
        recipientStakeholder.setRole(StakeholderRole.CURRENT_OWNER);
        recipientStakeholder.setNationalNumber(issueReqDto.getRecipientNN());
        recipientStakeholder.setCellphone(issueReqDto.getRecipientCellphone());
        recipientStakeholder.setFullName(issueReqDto.getRecipientFullName());
        recipientStakeholder.setPaymentPlace(issueReqDto.getPaymentPlace());
        recipientStakeholder.setRequest(request);
        stakeholders.add(recipientStakeholder);

        stakeholderRepository.saveAll(stakeholders);

    }

    private static PromissoryStakeholder getIssuerStakeholder(IssueFirstReqDto issueReqDto, PromissoryRequest request) {
        PromissoryStakeholder issuerStakeholder = new PromissoryStakeholder();
        issuerStakeholder.setRole(StakeholderRole.ISSUER);
        issuerStakeholder.setNationalNumber(issueReqDto.getIssuerNN());
        issuerStakeholder.setCellphone(issueReqDto.getIssuerCellphone());
        issuerStakeholder.setAddress(issueReqDto.getIssuerAddress());
        issuerStakeholder.setPostalCode(issueReqDto.getIssuerPostalCode());
        issuerStakeholder.setFullName(issueReqDto.getIssuerFullName());
        issuerStakeholder.setBirthdate(issueReqDto.getIssuerBirthdate());
        issuerStakeholder.setAccountNumber(issueReqDto.getIssuerAccountNumber());
        issuerStakeholder.setPaymentPlace(issueReqDto.getPaymentPlace());
        issuerStakeholder.setRequest(request);
        return issuerStakeholder;
    }

    public void createStakeholderForGuarantor(String guarantorNN, PromissoryRequest request) {
        PromissoryStakeholder guarantorStakeholder = new PromissoryStakeholder();
        guarantorStakeholder.setRole(StakeholderRole.GUARANTOR);
        guarantorStakeholder.setNationalNumber(guarantorNN);
        guarantorStakeholder.setRequest(request);
        stakeholderRepository.saveAndFlush(guarantorStakeholder);
    }

    public void updateStakeholderForGuarantor(GuaranteeRegisterReqDto guaranteeReqDto, PromissoryRequest request) {
        PromissoryStakeholder savedStakeholder = getStakeholderByRole(request, StakeholderRole.GUARANTOR);

        savedStakeholder.setCellphone(guaranteeReqDto.getGuaranteeCellphone());
        savedStakeholder.setAddress(guaranteeReqDto.getGuaranteeAddress());
        savedStakeholder.setFullName(guaranteeReqDto.getGuaranteeFullName());
        savedStakeholder.setAccountNumber(guaranteeReqDto.getGuaranteeAccountNumber());
        savedStakeholder.setPaymentPlace(guaranteeReqDto.getPaymentPlace());
        stakeholderRepository.saveAndFlush(savedStakeholder);
    }
}
