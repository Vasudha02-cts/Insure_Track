package com.insuretrack.service;

import com.insuretrack.dto.InvoiceRequestDTO;
import com.insuretrack.dto.InvoiceResponseDTO;
import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto);
    InvoiceResponseDTO getInvoiceById(Long id);
    List<InvoiceResponseDTO> getAllInvoices();
    void checkOverdueInvoices(); // Added so it can be called from outside
    /**
     * Finds all invoices (including the first pro-forma) associated with a specific Quote.
     */
    List<InvoiceResponseDTO> listByQuote(Long quoteID);
}