package com.insuretrack.repository;

import com.insuretrack.entity.Claim;
import com.insuretrack.entity.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    // Counts for Admin Dashboard
    long countByStatus(ClaimStatus status);

    // Fixed: Navigates Claim -> Policy -> Customer -> customerID
    List<Claim> findByPolicy_Customer_CustomerID(Long customerID);

    // Find claims by status and date
    List<Claim> findByStatusAndReportedDateBefore(ClaimStatus status, LocalDate date);
}