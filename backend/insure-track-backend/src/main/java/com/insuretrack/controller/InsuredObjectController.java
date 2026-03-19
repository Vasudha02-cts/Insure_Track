package com.insuretrack.controller;

import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.service.InsuredObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objects")
public class InsuredObjectController {

    @Autowired
    private InsuredObjectService insuredObjectService;

    // 1. Update valuation or details of an object
    @PutMapping("/{id}")
    public ResponseEntity<InsuredObjectResponseDTO> updateObject(
            @PathVariable Long id,
            @RequestBody InsuredObjectRequestDTO dto) {
        return ResponseEntity.ok(insuredObjectService.updateObject(id, dto));
    }

    // 2. Get details of a specific car/property
    @GetMapping("/{id}")
    public ResponseEntity<InsuredObjectResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(insuredObjectService.getObjectById(id));
    }

    // 3. Get all objects belonging to a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<InsuredObjectResponseDTO>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(insuredObjectService.getObjectsByCustomerId(customerId));
    }

    // 4. Delete an object (if customer sells the asset)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObject(@PathVariable Long id) {
        insuredObjectService.deleteObject(id);
        return ResponseEntity.noContent().build();
    }
}