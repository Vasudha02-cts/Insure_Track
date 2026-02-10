package com.insuretrack.identity.service;

import com.insuretrack.identity.entity.AuditLog;
import com.insuretrack.identity.entity.User;
import com.insuretrack.identity.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;
    public void log(User user, String action, String resource, String metadata){
        AuditLog log=new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(resource);
        log.setTimestamp(LocalDateTime.now());
        log.setMetadata(metadata);
        auditLogRepository.save(log);
    }
}
