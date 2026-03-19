package com.insuretrack.repository;

import com.insuretrack.entity.RegulatoryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegulatoryReportRepository extends JpaRepository<RegulatoryReport, Long> {
    List<RegulatoryReport> findByStatus(String status);
}