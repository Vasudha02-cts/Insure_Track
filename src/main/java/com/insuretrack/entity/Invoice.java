package com.insuretrack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceID;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private Double amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "PolicyID")
    private Policy policy;
}
