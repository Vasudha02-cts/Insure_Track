package com.insuretrack.service.impl;

import com.insuretrack.dto.EndorsementRequestDTO;
import com.insuretrack.dto.EndorsementResponseDTO;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.EndorsementChangeType;
import com.insuretrack.entity.enums.EndorsementStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.EndorsementMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.EndorsementService;
import com.insuretrack.service.RatingEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EndorsementServiceImpl implements EndorsementService {

    private final EndorsementRepository endorsementRepository;
    private final PolicyRepository policyRepository;
    private final CoverageRepository coverageRepository;
    private final RatingEngineService ratingEngine;
    private final EndorsementMapper endorsementMapper;
    private final InvoiceRepository invoiceRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public EndorsementResponseDTO createDraft(EndorsementRequestDTO req) {
        Policy policy = policyRepository.findById(req.getPolicyID())
                .orElseThrow(() -> new NotFoundException("Policy not found"));

        double rawDelta = 0.0;

        // 1. Determine Raw Cost based on the Change Type
        if (req.getChangeType() == EndorsementChangeType.AddCoverage) {
            rawDelta = calculateCoverageCost(req.getCoverageID()); // Positive cost
        }
        else if (req.getChangeType() == EndorsementChangeType.RemoveCoverage) {
            rawDelta = -calculateCoverageCost(req.getCoverageID()); // Negative cost (Credit)
        }
        else if (req.getChangeType() == EndorsementChangeType.LimitChange) {
            rawDelta = 50.0; // Flat administrative fee for changing limits
        }
        else if (req.getChangeType() == EndorsementChangeType.BeneficiaryChange) {
            rawDelta = 0.0; // Free update
        }

        // 2. Risk Adjustment via Rating Engine
        double riskAdjustedDelta = ratingEngine.calculateEndorsementDelta(policy, rawDelta);

        // 3. Map to Entity and Save
        Endorsement e = endorsementMapper.toEntity(req);
        e.setPremiumDelta(BigDecimal.valueOf(riskAdjustedDelta));
        e.setStatus(EndorsementStatus.Draft);

        return endorsementMapper.toResponse(endorsementRepository.save(e));
    }

    /**
     * Helper method to determine the base cost of specific coverages.
     */
    private double calculateCoverageCost(Long coverageId) {
        if (coverageId == null) return 0.0;

        Coverage coverage = coverageRepository.findById(coverageId)
                .orElseThrow(() -> new NotFoundException("Coverage not found with ID: " + coverageId));

        String type = coverage.getCoverageType().toUpperCase();

        if (type.contains("COLLISION")) return 300.0;
        if (type.contains("LIABILITY")) return 150.0;
        if (type.contains("FIRE")) return 100.0;

        return 0.0;
    }

    @Override
    public EndorsementResponseDTO approve(Long endorsementID) {
        Endorsement e = endorsementRepository.findById(endorsementID)
                .orElseThrow(() -> new NotFoundException("Endorsement not found"));
        e.setStatus(EndorsementStatus.Approved);
        return endorsementMapper.toResponse(endorsementRepository.save(e));
    }

    @Override
    @Transactional
    public EndorsementResponseDTO apply(Long endorsementID) {
        Endorsement e = endorsementRepository.findById(endorsementID)
                .orElseThrow(() -> new NotFoundException("Endorsement not found"));

        if (e.getStatus() != EndorsementStatus.Approved) {
            throw new IllegalStateException("Endorsement must be Approved before applying.");
        }

        Policy p = policyRepository.findById(e.getPolicyID())
                .orElseThrow(() -> new NotFoundException("Policy not found"));

        // 1. Update Policy Total Premium
        p.setPremium(p.getPremium().add(e.getPremiumDelta()));
        policyRepository.save(p);

        // 2. Adjust Monthly Invoices (Spread the delta over remaining open bills)
        List<Invoice> openInvoices = invoiceRepository.findByPolicy_PolicyID(p.getPolicyID())
                .stream()
                .filter(inv -> "Open".equalsIgnoreCase(inv.getStatus()))
                .collect(Collectors.toList());

        if (!openInvoices.isEmpty()) {
            BigDecimal monthlyIncrease = e.getPremiumDelta().divide(
                    BigDecimal.valueOf(openInvoices.size()), 2, RoundingMode.HALF_UP);

            for (Invoice inv : openInvoices) {
                inv.setAmount(inv.getAmount() + monthlyIncrease.doubleValue());
            }
            invoiceRepository.saveAll(openInvoices);
        }

        // 3. Finalize Endorsement
        e.setStatus(EndorsementStatus.Applied);

        // 4. Trigger Notification
        Notification note = new Notification();
        note.setUserID(p.getCustomer().getUser().getUserID());
        note.setCategory("POLICY_UPDATE");
        note.setMessage("Endorsement applied successfully! Your monthly payment has been updated.");
        note.setCreatedDate(LocalDateTime.now());
        note.setStatus("Unread");
        notificationRepository.save(note);

        return endorsementMapper.toResponse(endorsementRepository.save(e));
    }

    @Override
    public List<EndorsementResponseDTO> listByPolicy(Long policyID) {
        return endorsementRepository.findByPolicy_PolicyID(policyID).stream()
                .map(endorsementMapper::toResponse)
                .collect(Collectors.toList());
    }
}