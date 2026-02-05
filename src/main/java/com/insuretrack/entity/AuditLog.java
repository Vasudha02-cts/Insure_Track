package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AuditID;

    private String Action;
    private String Resource;
    private LocalDateTime  Timestamp;
    private String Metadata;

    @ManyToOne
    @JoinColumn(name="UserID")
    private User user;
}
