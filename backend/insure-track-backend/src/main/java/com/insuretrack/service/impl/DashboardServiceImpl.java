package com.insuretrack.service.impl;

import com.insuretrack.dto.*;
import com.insuretrack.entity.enums.ClaimStatus;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.mapper.*;
import com.insuretrack.repository.*;
import com.insuretrack.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PolicyRepository policyRepository;
    private final ClaimRepository claimRepository;
    private final InvoiceRepository invoiceRepository;
    private final QuoteRepository quoteRepository;

    // Mappers for the lists in CustomerDashboard
    private final PolicyMapper policyMapper;
    private final ClaimMapper claimMapper;
    private final BillingMapper billingMapper;

    @Override
    @Transactional(readOnly = true)
    public AdminSummaryDTO getAdminSummary() {
        AdminSummaryDTO dto = new AdminSummaryDTO();

        dto.setTotalPolicies(policyRepository.count());

        // Summing active revenue from our custom query in PolicyRepository
        Double revenue = policyRepository.calculateTotalActiveRevenue();
        dto.setTotalRevenue(revenue != null ? revenue : 0.0);

        // Claims counts using enum statuses
        dto.setOpenClaimsCount(claimRepository.countByStatus(ClaimStatus.OPEN));
        // Priority is often a String in your repo, checking High priority
        dto.setUrgentClaimsCount(claimRepository.countByStatus(ClaimStatus.INVESTIGATING));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDashboardDTO getCustomerDashboard(Long customerID) {
        CustomerDashboardDTO dto = new CustomerDashboardDTO();

        // 1. Get My Policies
        dto.setMyPolicies(policyRepository.findByCustomer_CustomerID(customerID).stream()
                .map(policyMapper::toResponse)
                .collect(Collectors.toList()));

        // 2. Get My Claims
        dto.setMyClaims(claimRepository.findByPolicy_Customer_CustomerID(customerID).stream()
                .map(claimMapper::toResponse)
                .collect(Collectors.toList()));

        // 3. Get Pending Invoices
        // Fetching "Open" or "Overdue" status invoices
        dto.setPendingInvoices(invoiceRepository.findByPolicy_Customer_CustomerID(customerID).stream()
                .filter(inv -> !"Paid".equalsIgnoreCase(inv.getStatus()))
                .map(billingMapper::toInvoiceResponse)
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public RevenueReportDTO getRevenueReport() {
        RevenueReportDTO report = new RevenueReportDTO();
        report.setTotalPremiumCollected(policyRepository.calculateTotalActiveRevenue());
        report.setActivePolicyCount((int) policyRepository.count());
        // Additional analytics logic can be added here
        return report;
    }
}