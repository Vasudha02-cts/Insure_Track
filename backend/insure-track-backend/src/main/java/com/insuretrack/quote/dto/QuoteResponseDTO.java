package com.insuretrack.quote.dto;

import com.insuretrack.common.enums.QuoteStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
//@Builder
public class QuoteResponseDTO {
    private Long quoteId;
    private Double premium;
    private String status;
    private LocalDateTime createdDate;
}
