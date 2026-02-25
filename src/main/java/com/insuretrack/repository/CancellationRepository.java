package com.insuretrack.repository;

import com.insuretrack.entity.Cancellation;
import com.insuretrack.entity.enums.CancellationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository for Cancellation CRUD and finders. */
public interface CancellationRepository extends JpaRepository<Cancellation, Long> {
    List<Cancellation> findByPolicyID(Long policyID);
    List<Cancellation> findByStatus(CancellationStatus status);
    List<Cancellation> findByPolicy_PolicyID(Long policyID);
}