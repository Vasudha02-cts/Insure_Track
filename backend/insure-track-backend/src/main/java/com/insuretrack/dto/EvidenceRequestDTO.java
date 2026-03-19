package com.insuretrack.dto;

import lombok.Data;

@Data
public class EvidenceRequestDTO {
    private String type; // e.g., "Photo", "Police Report"
    private String uri;  // File path or URL
}