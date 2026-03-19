package com.insuretrack.entity;

import com.insuretrack.entity.enums.CommonStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Invoice")
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceID;

    private LocalDate issueDate;
    private LocalDate dueDate;
    private Double amount; // (Annual Premium / 12) + Fees
    private String status; // Open, Paid, Overdue
    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = true) // Nullable because it might be a Quote Invoice
    private Policy policy;

    @ManyToOne
    @JoinColumn(name = "quote_id", nullable = true) // Link to Quote for the first payment
    private Quote quote;

    // Helper to check if it's the first one
    public boolean isFirstInstallment() {
        return this.quote != null && this.policy == null;
    }
}