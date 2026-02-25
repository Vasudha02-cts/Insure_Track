package com.insuretrack.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class QuoteRequestDTO {
    private Long customerID;
    private Long productID;
    private Long insuredObjectID; // Link to Akhila's BMW
    private String coveragesJSON;  // List of selected coverage IDs and their costs
}