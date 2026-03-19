package com.insuretrack.repository;

import com.insuretrack.entity.Renewal;
import com.insuretrack.entity.enums.RenewalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository for Renewal CRUD and finders. */
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    List<Renewal> findByPolicyID(Long policyID);
    List<Renewal> findByStatus(RenewalStatus status);
    List<Renewal> findByPolicy_PolicyID(Long policyID);
}