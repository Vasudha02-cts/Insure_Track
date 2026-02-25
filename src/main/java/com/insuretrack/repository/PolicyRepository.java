package com.insuretrack.repository;

import com.insuretrack.entity.Policy;
import com.insuretrack.entity.enums.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findByPolicyNumber(String policyNumber);

    // Fixed: Matches policy.quote.quoteID
    List<Policy> findByQuote_QuoteID(Long quoteID);

    List<Policy> findByStatus(PolicyStatus status);

    // Matches policy.customer.customerID
    List<Policy> findByCustomer_CustomerID(Long customerID);

    @Query("SELECT SUM(p.premium) FROM Policy p WHERE p.status = 'Active'")
    Double calculateTotalActiveRevenue();
}