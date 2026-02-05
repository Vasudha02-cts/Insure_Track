package com.insuretrack.repository;

import com.insuretrack.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUser_UserID(Long userID); // Track activity by user [cite: 51]
}
