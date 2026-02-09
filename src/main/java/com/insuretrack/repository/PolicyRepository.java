package com.insuretrack.repository;

import com.insuretrack.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    // If your field is policyNumber, this must be findByPolicyNumber
    Optional<Policy> findByPolicyNumber(String policyNumber);
}