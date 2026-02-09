package com.insuretrack.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "UnderwritingCase")
@Data
@NoArgsConstructor
public class UnderwritingCase {

    // Defined based on your module requirements
    public enum DecisionStatus { Approve, Decline, Conditional }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UWCaseID")
    private Long uwCaseID;

    @Column(name = "QuoteID")
    private Long quoteID;

    @Column(name = "UnderwriterID")
    private Long underwriterID;

    @Column(name = "RiskAssessment", columnDefinition = "TEXT")
    private String riskAssessment;

    @Enumerated(EnumType.STRING)
    @Column(name = "Decision")
    private DecisionStatus decision; // Approve/Decline/Conditional

    @Column(name = "Conditions", columnDefinition = "TEXT")
    private String conditions;

    @Column(name = "DecisionDate")
    private LocalDateTime decisionDate;

    // Automatically set the date when a decision is made
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        decisionDate = LocalDateTime.now();
    }
}