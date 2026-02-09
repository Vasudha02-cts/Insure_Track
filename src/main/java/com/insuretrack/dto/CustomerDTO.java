package com.insuretrack.dto;

import com.insuretrack.entity.enums.CustomerSegment;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long customerID;
    private Long userID;
    private String name;
    private LocalDate dob;
    private CustomerSegment segment; // RETAIL, SME, CORPORATE
    private String status; // ACTIVE, INACTIVE

    // Flattened contact details for the API request
    private String email;
    private String phone;
    private String contactInfo;
}