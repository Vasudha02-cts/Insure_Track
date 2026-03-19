package com.insuretrack.service;

import com.insuretrack.dto.UnderwritingRequestDTO;
import com.insuretrack.dto.UnderwritingResponseDTO;

public interface UnderwritingService {
    UnderwritingResponseDTO reviewQuote(UnderwritingRequestDTO request);
    UnderwritingResponseDTO getHistoryByQuote(Long quoteId);
}