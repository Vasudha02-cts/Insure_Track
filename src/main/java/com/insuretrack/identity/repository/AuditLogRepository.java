package com.insuretrack.identity.repository;

import com.insuretrack.identity.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

}
