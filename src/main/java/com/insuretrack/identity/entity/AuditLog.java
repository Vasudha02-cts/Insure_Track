package com.insuretrack.identity.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="auditlog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;
    @ManyToOne
    @JoinColumn(name="userID",nullable = false)
    private User user;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
    @Column(columnDefinition = "TEXT")
    private String metadata;
    public Long getAuditID() {
        return auditID;
    }

    public void setAuditID(Long auditID) {
        this.auditID = auditID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
