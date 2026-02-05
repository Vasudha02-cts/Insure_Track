package com.insuretrack.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UnderwritingCase")
public class UnderwritingCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uwCaseID;
    private Long quoteID;
    private Long underwriterID;
    private String riskAssessment;
    private String decision;
    private String conditions;
    private LocalDateTime decisionDate;
}
