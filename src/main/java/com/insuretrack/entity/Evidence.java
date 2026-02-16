package com.insuretrack.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Evidence")
@Data
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EvidenceID")
    private Long evidenceID;

    // Relationship: Multiple pieces of evidence can belong to one Claim
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID", nullable = false)
    private Claim claim;

    @Column(name = "Type")
    private String type; // Photo, Document, Medical, Police Report [cite: 203]

    @Column(name = "URI")
    private String uri; // Link to the file storage path [cite: 204]

    @Column(name = "UploadedDate")
    private LocalDateTime uploadedDate;

    // Getters and Setters...
}