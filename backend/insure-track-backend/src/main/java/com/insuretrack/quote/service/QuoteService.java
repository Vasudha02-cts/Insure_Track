package com.insuretrack.quote.service;

import com.insuretrack.customer.entity.InsuredObject;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.product.entity.Product;
import com.insuretrack.product.entity.RatingRule;
import com.insuretrack.quote.dto.QuoteRequestDTO;
import com.insuretrack.quote.dto.QuoteResponseDTO;

import java.util.List;

public interface QuoteService {
    QuoteResponseDTO createQuote(QuoteRequestDTO request);
    //QuoteResponseDTO calculatePremium(Long quoteId);
    QuoteResponseDTO submitQuote(Long quoteId);
    //Policy issuePolicy(Long quoteId);
    QuoteResponseDTO rateQuote(Long quoteId);
//    Double calculatePremium(Long objectId,Long productId);
}
