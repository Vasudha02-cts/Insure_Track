package com.insuretrack.entity;

import com.insuretrack.entity.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reserve")
@Data
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClaimID")
    private Claim claim;

    private Double amount;
    private LocalDateTime setDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

}
