package com.insuretrack.dto;

import lombok.Data;

@Data
public class BeneficiaryRequestDTO {
    private String name;
    private String relationship;
    private Double percentageShare;
}