package com.insuretrack.billing.repository;

import com.insuretrack.billing.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund,Long> {

}
