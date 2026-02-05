package com.insuretrack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "AuditLog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;

    private String action;
    private String resource;
    private LocalDateTime timestamp;
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonBackReference // This tells Jackson NOT to serialize the User again inside the AuditLog
    private User user;
}


