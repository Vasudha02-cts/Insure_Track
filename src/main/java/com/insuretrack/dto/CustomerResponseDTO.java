package com.insuretrack.dto;

import com.insuretrack.entity.enums.CustomerSegment;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerResponseDTO {
    private Long customerID;
    private Long userID; // Linked user ID
    private String name;
    private String email;
    private String phone;
    private LocalDate dob;
    private CustomerSegment segment;
    private String status;

    // --- Module 2: The Merged Lists ---
    private List<BeneficiaryResponseDTO> beneficiaries;
    private List<InsuredObjectResponseDTO> insuredObjects;
}