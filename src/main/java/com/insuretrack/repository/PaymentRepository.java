package com.insuretrack.repository;

import com.insuretrack.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Retrieve all payment history for a specific invoice
    List<Payment> findByInvoice_InvoiceID(Long invoiceID);

    // Filter payments by method (e.g., all "UPI" transactions)
    List<Payment> findByMethod(String method);

    // Find all successful payments for reconciliation
    List<Payment> findByStatus(String status);
}