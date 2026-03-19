package com.insuretrack.entity;

import com.insuretrack.entity.enums.QuoteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Table: Quote
 * This entity manages the link between the Customer, the Product, and the Asset (InsuredObject).
 */
@Data
@Entity
@Table(name = "Quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteID;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Service uses .setCustomer()

    @ManyToOne
    @JoinColumn(name = "insured_object_id")
    private InsuredObject insuredObject;

    private Long productID;
    private String coveragesJSON;
    private BigDecimal premium; // Added for 'Cannot resolve symbol premium'
    private LocalDate createdDate; // Added for 'Cannot resolve symbol createdDate'

    @Enumerated(EnumType.STRING)
    private QuoteStatus status;
}