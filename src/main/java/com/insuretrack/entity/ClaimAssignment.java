package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ClaimAssignment")
public class ClaimAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Long assignmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID", nullable = false)
    private Claim claim;

    @Column(name = "AdjusterID")
    private Long adjusterID; // Links to a User with 'Adjuster' role

    @Column(name = "AssignmentDate")
    private LocalDateTime assignmentDate;

    @Column(name = "Priority")
    private String priority; // Low, Medium, High [cite: 184]

    // Getters and Setters
}