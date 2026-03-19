package com.insuretrack.billing.repository;

import com.insuretrack.billing.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByInvoiceInvoiceId(Long invoiceId);
}
