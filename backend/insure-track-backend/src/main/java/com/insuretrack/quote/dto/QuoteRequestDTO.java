package com.insuretrack.quote.dto;

import lombok.Data;

@Data
public class QuoteRequestDTO {
    private Long customerId;
    private Long productId;
    private Long insuredObjectId;
    private String coveragesJSON;
}
