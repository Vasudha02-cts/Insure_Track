package com.insuretrack.controller;

import com.insuretrack.dto.BeneficiaryRequestDTO;
import com.insuretrack.dto.BeneficiaryResponseDTO;
import com.insuretrack.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    // 1. Update an existing beneficiary (Use this for your Step B test)
    @PutMapping("/{id}")
    public ResponseEntity<BeneficiaryResponseDTO> updateBeneficiary(
            @PathVariable Long id,
            @RequestBody BeneficiaryRequestDTO dto) {
        return ResponseEntity.ok(beneficiaryService.updateBeneficiary(id, dto));
    }

    // 2. Get all beneficiaries for a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BeneficiaryResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(beneficiaryService.getBeneficiariesByCustomerId(customerId));
    }

    // 3. Remove a beneficiary
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBeneficiary(@PathVariable Long id) {
        beneficiaryService.removeBeneficiary(id);
        return ResponseEntity.noContent().build();
    }
}