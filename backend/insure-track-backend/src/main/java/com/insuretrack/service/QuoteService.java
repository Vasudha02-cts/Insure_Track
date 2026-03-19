package com.insuretrack.service;

import com.insuretrack.dto.QuoteRequestDTO;
import com.insuretrack.dto.QuoteResponseDTO;
import com.insuretrack.entity.enums.QuoteStatus;
import java.util.List;

public interface QuoteService {
    // Change createQuote to createDraftQuote to match your implementation
    QuoteResponseDTO createQuote(QuoteRequestDTO dto);

    // Add this to match your implementation
    QuoteResponseDTO submitForRating(Long quoteID);

    QuoteResponseDTO getById(Long quoteID);
    List<QuoteResponseDTO> listByCustomer(Long customerID);
    QuoteResponseDTO updateStatus(Long quoteID, QuoteStatus status);
}