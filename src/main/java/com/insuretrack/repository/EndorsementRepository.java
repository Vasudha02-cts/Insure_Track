package com.insuretrack.repository;

import com.insuretrack.entity.Endorsement;
import com.insuretrack.entity.enums.EndorsementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository for Endorsement CRUD and finders. */
public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    List<Endorsement> findByPolicyID(Long policyID);
    List<Endorsement> findByStatus(EndorsementStatus status);
    // Fixed: Navigates from Endorsement -> Policy -> policyID
    List<Endorsement> findByPolicy_PolicyID(Long policyID);
}