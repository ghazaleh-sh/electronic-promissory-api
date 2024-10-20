package ir.co.sadad.promissory.repositories;

import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import ir.co.sadad.promissory.entities.PromissoryStakeholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromissoryStakeholderRepository extends JpaRepository<PromissoryStakeholder, Long>, JpaSpecificationExecutor<PromissoryStakeholder> {

    List<PromissoryStakeholder> findByRequest(PromissoryRequest request);
    Optional<PromissoryStakeholder> findByRequestAndRole(PromissoryRequest request, StakeholderRole role);
}
