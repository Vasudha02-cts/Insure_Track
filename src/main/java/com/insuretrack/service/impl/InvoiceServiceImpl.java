package com.insuretrack.service.impl;

import com.insuretrack.dto.InvoiceRequestDTO;
import com.insuretrack.dto.InvoiceResponseDTO;
import com.insuretrack.entity.Invoice;
import com.insuretrack.entity.Notification;
import com.insuretrack.entity.User;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.InvoiceMapper;
import com.insuretrack.repository.InvoiceRepository;
import com.insuretrack.repository.NotificationRepository;
import com.insuretrack.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        if (dto.getDueDate().isBefore(dto.getIssueDate())) {
            throw new IllegalArgumentException("Due date cannot be earlier than issue date.");
        }

        Invoice invoice = invoiceMapper.toEntity(dto);

        if (invoice.getStatus() == null) {
            invoice.setStatus("OPEN");
        }

        Invoice saved = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponseDTO> listByQuote(Long quoteID) {
        // Fetch from repository using the custom method
        List<Invoice> invoices = invoiceRepository.findByQuote_QuoteID(quoteID);

        // Map to Response DTOs
        return invoices.stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());
    }
    // FIX: Implementation of the missing method
    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with ID: " + id));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    @Transactional
    public void checkOverdueInvoices() {
        List<Invoice> overdueInvoices = invoiceRepository.findAll().stream()
                .filter(i -> "OPEN".equalsIgnoreCase(i.getStatus()))
                .filter(i -> i.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        overdueInvoices.forEach(i -> {
            i.setStatus("OVERDUE");
            invoiceRepository.save(i);
        });
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());
    }
    // Inside InvoiceServiceImpl.java

    @Scheduled(cron = "0 0 1 * * ?") // Runs every day at 1:00 AM
    @Transactional
    public void processOverdueInvoices() {
        LocalDate today = LocalDate.now();

        // 1. Find all Invoices that are 'Open' and past their due date
        List<Invoice> overdueInvoices = invoiceRepository.findByStatusAndDueDateBefore("Open", today);

        for (Invoice inv : overdueInvoices) {
            // 2. Determine who to notify
            User user = null;
            String policyRef = "";

            if (inv.getPolicy() != null) {
                user = inv.getPolicy().getCustomer().getUser();
                policyRef = "Policy: " + inv.getPolicy().getPolicyNumber();
            } else if (inv.getQuote() != null) {
                user = inv.getQuote().getCustomer().getUser();
                policyRef = "Quote: #" + inv.getQuote().getQuoteID();
            }

            if (user != null) {
                // 3. Trigger Notification
                Notification note = new Notification();
                note.setUserID(user.getUserID());
                note.setCategory("PAYMENT_OVERDUE");
                note.setMessage("URGENT: Your payment for " + policyRef + " is overdue by " +
                        java.time.temporal.ChronoUnit.DAYS.between(inv.getDueDate(), today) + " days.");
                note.setCreatedDate(LocalDateTime.now());
                note.setStatus("Unread");
                notificationRepository.save(note);
            }
        }
    }
}