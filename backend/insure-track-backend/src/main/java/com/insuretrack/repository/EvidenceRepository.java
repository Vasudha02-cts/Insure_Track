package com.insuretrack.repository;

import com.insuretrack.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    // Retrieve all documents/photos for a specific claim
    List<Evidence> findByClaim_ClaimID(Long claimID);

    // Filter evidence by type (e.g., show only "Police Report")
    List<Evidence> findByType(String type);
}