package com.insuretrack.repository;

import com.insuretrack.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
    // This allows us to find all refunds for a specific payment to prevent over-refunding
    List<Refund> findByPayment_PaymentID(Long paymentID);
}