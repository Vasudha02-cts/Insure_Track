package com.insuretrack.billing.repository;

import com.insuretrack.billing.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByPolicyPolicyId(Long policyId);
}
