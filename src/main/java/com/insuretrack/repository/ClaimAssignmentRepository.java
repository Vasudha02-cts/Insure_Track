package com.insuretrack.repository;

import com.insuretrack.entity.ClaimAssignment;
import com.insuretrack.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimAssignmentRepository extends JpaRepository<ClaimAssignment, Long> {
    List<ClaimAssignment> findByClaim_ClaimID(Long claimID);
}