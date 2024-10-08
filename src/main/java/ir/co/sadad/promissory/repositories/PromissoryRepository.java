package ir.co.sadad.promissory.repositories;

import ir.co.sadad.promissory.entities.Promissory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromissoryRepository extends JpaRepository<Promissory, Long>, JpaSpecificationExecutor<Promissory> {

    Optional<Promissory> findByPromissoryUid(String promissoryUid);
}
