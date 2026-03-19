package com.insuretrack.user.repository;

import com.insuretrack.user.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
