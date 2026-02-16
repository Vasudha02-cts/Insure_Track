package com.insuretrack.repository;

import com.insuretrack.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    // CHANGE: Return a single Settlement instead of a List
    Settlement findByClaim_ClaimID(Long claimID);

    // If you need to find multiple by status, List is fine here
    List<Settlement> findByStatus(String status);
}