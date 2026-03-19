package com.insuretrack.controller;

import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.dto.PaymentReceiptDTO;
import com.insuretrack.dto.PaymentRequestDTO;
import com.insuretrack.dto.PaymentResponseDTO;
import com.insuretrack.service.InvoiceService;
import com.insuretrack.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    /**
     * Create a new payment and reconcile the associated invoice.
     */
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody PaymentRequestDTO dto) {
        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    /**
     * Retrieve a specific payment record by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    /**
     * List all payments (Finance/Admin View).
     */
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    /**
     * Get all payments for a specific invoice.
     * Useful for Akhil's portal to see payment history for one bill.
     */
    @GetMapping("/invoice/{invoiceID}")
    public ResponseEntity<List<PaymentResponseDTO>> getByInvoice(@PathVariable Long invoiceID) {
        // We can call the repository logic via a new service method if needed,
        // or filter the 'getAll' results.
        return ResponseEntity.ok(paymentService.getAllPayments().stream()
                .filter(p -> p.getInvoiceID().equals(invoiceID))
                .toList());
    }
    @GetMapping("/{id}/receipt")
    public ResponseEntity<PaymentReceiptDTO> getReceipt(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getReceipt(id));
    }

}