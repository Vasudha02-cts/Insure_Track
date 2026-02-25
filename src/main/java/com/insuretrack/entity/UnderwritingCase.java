package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "UnderwritingCase")
@Data
public class UnderwritingCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uwCaseID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id")
    private Quote quote;

    private Long underwriterID;
    @Column(columnDefinition = "TEXT")
    private String riskAssessment;
    private String decision;
    private String conditions;
    private LocalDateTime decisionDate;
}