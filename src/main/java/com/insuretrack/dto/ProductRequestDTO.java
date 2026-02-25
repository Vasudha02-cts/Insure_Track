package com.insuretrack.dto;

import lombok.Data;

/**
 * DTO for creating or updating a Product.
 * Matches the fields required by the 'Product' table in the PDF.
 */
@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private String status;
}