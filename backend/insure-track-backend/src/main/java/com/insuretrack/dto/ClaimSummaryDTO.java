package com.insuretrack.dto;

import lombok.Data;
import java.util.List;

@Data
public class ClaimSummaryDTO {
    private ClaimResponseDTO claimDetails;
    private List<ClaimAssignmentResponseDTO> assignments;
    private List<ReserveResponseDTO> reserves;
    private List<EvidenceResponseDTO> evidence;
    private SettlementResponseDTO settlement;
}