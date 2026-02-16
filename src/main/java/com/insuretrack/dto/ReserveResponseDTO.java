package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReserveResponseDTO {
    private Long reserveID;
    private Long claimID;
    private Double amount;
    private LocalDateTime setDate;
    private LocalDateTime updatedDate;
    private String status; // OPEN, ADJUSTED, RELEASED [cite: 192]
}
