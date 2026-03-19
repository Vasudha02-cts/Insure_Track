package com.insuretrack.service.impl;

import com.insuretrack.entity.AuditLog;
import com.insuretrack.entity.User;
import com.insuretrack.repository.AuditLogRepository;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void logAction(User user, String action, String resource, String metadata) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(resource);
        log.setTimestamp(LocalDateTime.now());
        log.setMetadata(metadata);

        auditLogRepository.save(log);
    }
    @Override
    public void logAction(Long userId,String action, String resource, String metadata){
        User user = userRepository.findById(userId).orElse(null);
        logAction(user,action,resource, metadata);
    }
}
