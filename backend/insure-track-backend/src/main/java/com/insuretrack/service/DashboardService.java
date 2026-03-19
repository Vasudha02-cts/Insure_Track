package com.insuretrack.service;

import com.insuretrack.dto.AdminSummaryDTO;
import com.insuretrack.dto.CustomerDashboardDTO;
import com.insuretrack.dto.RevenueReportDTO;

public interface DashboardService {
    AdminSummaryDTO getAdminSummary();
    CustomerDashboardDTO getCustomerDashboard(Long customerID);
    RevenueReportDTO getRevenueReport();
}