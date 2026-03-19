package com.insuretrack.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuditLogResponseDTO {
    private Long auditID;
    private Long userID;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
    private String metadata;
}