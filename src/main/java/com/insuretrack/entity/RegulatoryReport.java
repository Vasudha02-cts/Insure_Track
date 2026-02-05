package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "RegulatoryReport")
public class RegulatoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private Long reportID;

    @Column(name = "ReportType")
    private String reportType; // Solvency/Compliance/Claims/Financial [cite: 254]

    @Column(name = "Period")
    private String period;

    @Column(name = "GeneratedDate")
    private LocalDateTime generatedDate;

    @Column(name = "Status")
    private String status; // Draft/Submitted [cite: 257]

    // Getters and Setters
}