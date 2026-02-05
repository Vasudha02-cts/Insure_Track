package com.insuretrack.repository;

import com.insuretrack.entity.ClaimAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimAssignmentRepository extends JpaRepository<ClaimAssignment, Long> {}