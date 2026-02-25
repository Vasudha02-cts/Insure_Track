package com.insuretrack.controller;

import com.insuretrack.dto.InvoiceRequestDTO;
import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    /**
     * Create a new invoice (Usually triggered after Policy Binding)
     */
    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.createInvoice(dto));
    }
    @GetMapping("/quote/{quoteId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByQuote(@PathVariable Long quoteId) {
        return ResponseEntity.ok(invoiceService.listByQuote(quoteId));
    }
    /**
     * Get details of a specific invoice
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    /**
     * List all invoices (Admin/Finance View)
     */
    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAll() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    /**
     * Manually trigger the overdue status update logic
     * Useful for the Finance/Admin Dashboard
     */
    @PostMapping("/check-overdue")
    public ResponseEntity<String> runOverdueCheck() {
        invoiceService.checkOverdueInvoices();
        return ResponseEntity.ok("Overdue check completed. All late invoices updated to OVERDUE status.");
    }
}