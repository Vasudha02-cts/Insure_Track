package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EvidenceResponseDTO {
    private Long evidenceID;
    private Long claimID;
    private String type;
    private String uri;
    private LocalDateTime uploadedDate;
}
