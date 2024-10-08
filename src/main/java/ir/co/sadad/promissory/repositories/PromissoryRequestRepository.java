package ir.co.sadad.promissory.repositories;

import ir.co.sadad.promissory.commons.enums.RequestType;
import ir.co.sadad.promissory.entities.PromissoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromissoryRequestRepository extends JpaRepository<PromissoryRequest, Long>, JpaSpecificationExecutor<PromissoryRequest> {

    Optional<PromissoryRequest> findByRequestUid(String requestUid);

    Optional<List<PromissoryRequest>> findByPromissory_PromissoryUidAndRequestType(String promissoryUid, RequestType requestType);
}
