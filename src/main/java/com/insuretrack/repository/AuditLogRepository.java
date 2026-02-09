package com.insuretrack.repository;

import com.insuretrack.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // Find logs associated with a specific user [cite: 51, 276]
    List<AuditLog> findByUser_UserID(Long userID);

    // Find logs by a specific action like "LOGIN" or "REGISTER" [cite: 53]
    List<AuditLog> findByAction(String action);
}