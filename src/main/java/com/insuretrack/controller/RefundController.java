package com.insuretrack.controller;

import com.insuretrack.dto.RefundRequestDTO;
import com.insuretrack.dto.RefundResponseDTO;
import com.insuretrack.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponseDTO> create(@RequestBody RefundRequestDTO dto) {
        return ResponseEntity.ok(refundService.createRefund(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(refundService.getRefundById(id));
    }

    @GetMapping
    public ResponseEntity<List<RefundResponseDTO>> getAll() {
        return ResponseEntity.ok(refundService.getAllRefunds());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RefundResponseDTO> delete(@PathVariable Long id) {
        // Now returns the deleted DTO details per our updated service
        return ResponseEntity.ok(refundService.deleteRefund(id));
    }
}