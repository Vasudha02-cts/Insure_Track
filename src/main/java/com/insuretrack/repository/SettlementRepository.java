package com.insuretrack.repository;

import com.insuretrack.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    // Find settlements for a specific claim
    List<Settlement> findByClaim_ClaimID(Long claimID);

    // Filter by payment status (e.g., "Pending" or "Paid")
    List<Settlement> findByStatus(String status);
}