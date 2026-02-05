package com.insuretrack.repository;

import com.insuretrack.entity.RiskMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskMetricRepository extends JpaRepository<RiskMetric, Long> {
}