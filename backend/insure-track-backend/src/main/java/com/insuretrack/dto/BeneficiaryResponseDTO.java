package com.insuretrack.dto;

import lombok.Data;

@Data
public class BeneficiaryResponseDTO {
    private Long beneficiaryID;
    private String name;
    private String relationship;
    private Double percentageShare;
}
