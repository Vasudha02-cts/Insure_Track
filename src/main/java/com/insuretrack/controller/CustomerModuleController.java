package com.insuretrack.controller;

import com.insuretrack.dto.*;
import com.insuretrack.service.CustomerService;
import com.insuretrack.service.InsuredObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerModuleController {

    @Autowired
    private CustomerService customerService;

    private InsuredObjectService insuredObjectService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createProfile(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(customerService.createCustomerProfile(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerProfile(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> listAll() {
        return ResponseEntity.ok(customerService.getAllProfiles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/objects/{id}")
    public ResponseEntity<InsuredObjectResponseDTO> updateObject(
            @PathVariable Long id,
            @RequestBody InsuredObjectRequestDTO dto) {
        return ResponseEntity.ok(insuredObjectService.updateObject(id, dto));
    }
}