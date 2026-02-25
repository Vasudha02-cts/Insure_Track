package com.insuretrack.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDashboardDTO {
    private List<PolicyResponseDTO> myPolicies;
    private List<ClaimResponseDTO> myClaims;
    private List<InvoiceResponseDTO> pendingInvoices;
}