package ir.co.sadad.promissory.repositories;

import ir.co.sadad.promissory.entities.PromissoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PromissoryLogRepository extends JpaRepository<PromissoryLog, Long>, JpaSpecificationExecutor<PromissoryLog> {
}
