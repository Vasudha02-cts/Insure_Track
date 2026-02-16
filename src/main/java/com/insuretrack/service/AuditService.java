package com.insuretrack.service;

import com.insuretrack.entity.User;

public interface AuditService {
    // Log using the full User object
    void logAction(User user, String action, String resource, String metadata);

    // Log using just the UserID (Useful for automated or background tasks)
    void logAction(Long userId, String action, String resource, String metadata);
}