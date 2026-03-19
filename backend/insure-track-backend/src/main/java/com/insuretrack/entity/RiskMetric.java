package com.insuretrack.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "RiskMetric")
public class RiskMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MetricID")
    private Long metricID;

    @Column(name = "Scope")
    private String scope; // Product/Portfolio/Period [cite: 246]

    @Column(name = "Metrics")
    private String metrics; // LossRatio, Combined Ratio, etc. [cite: 249]

    @Column(name = "AsOfDate")
    private LocalDate asOfDate;

    // Getters and Setters
}