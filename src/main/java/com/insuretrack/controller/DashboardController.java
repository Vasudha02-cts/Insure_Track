package com.insuretrack.controller;

import com.insuretrack.dto.AdminSummaryDTO;
import com.insuretrack.dto.CustomerDashboardDTO;
import com.insuretrack.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/admin")
    public ResponseEntity<AdminSummaryDTO> getAdminStats() {
        return ResponseEntity.ok(dashboardService.getAdminSummary());
    }

    @GetMapping("/customer/{customerID}")
    public ResponseEntity<CustomerDashboardDTO> getCustomerStats(@PathVariable Long customerID) {
        return ResponseEntity.ok(dashboardService.getCustomerDashboard(customerID));
    }
}