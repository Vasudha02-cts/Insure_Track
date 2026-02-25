package com.insuretrack.dto;

import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.CustomerSegment;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerRequestDTO {
    // User Account Info
    private String email;
    private Long phone;
    private String password; // Crucial for registration
    private Long userID;
    // Profile Info
    private String name;
    private LocalDate dob;
    private CustomerSegment segment;
    private String contactInfo;
    private CommonStatus status; // Added from Module 2

    // --- Module 2: Extended Data (The "Merge" fix) ---
    private List<BeneficiaryRequestDTO> beneficiaries;
    private List<InsuredObjectRequestDTO> insuredObjects;


}