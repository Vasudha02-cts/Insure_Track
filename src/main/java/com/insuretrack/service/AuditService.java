package com.insuretrack.service;

import com.insuretrack.entity.User;

public interface AuditService {
    /**
     * Requirement: Record system actions for audit trails [cite: 16, 28, 39, 48-57].
     */
    void logAction(User user, String action, String resource, String metadata);
}