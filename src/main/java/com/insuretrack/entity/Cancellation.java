package com.insuretrack.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Cancellation")
public class Cancellation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancellationID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PolicyID")
    private Policy policy;

    private String reason;
    private LocalDate effectiveDate;
    private Double refundAmount;
    private String status; // Requested/Approved/Processed [cite: 159]
}