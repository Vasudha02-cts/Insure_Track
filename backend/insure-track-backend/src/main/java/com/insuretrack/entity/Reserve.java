package com.insuretrack.entity;

import com.insuretrack.entity.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Reserve")
public class Reserve {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveID;

    @OneToOne @JoinColumn(name = "claim_id")
    private Claim claim;

    private Double amount; // The calculated reserve
    private LocalDate setDate;
    private String status; // Open, Adjusted, Released
}