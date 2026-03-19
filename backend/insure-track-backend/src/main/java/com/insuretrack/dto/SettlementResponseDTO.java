package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SettlementResponseDTO {
    private Long settlementID;
    private Long claimID;
    private Double settlementAmount;
    private LocalDateTime settlementDate;
    private String paymentReference;
    private String status;
}