package com.insuretrack.service.impl;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.ClaimStatus;
import com.insuretrack.entity.enums.PaymentStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.ClaimMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;
    private final ReserveRepository reserveRepository;
    private final SettlementRepository settlementRepository;
    private final ClaimAssignmentRepository assignmentRepository;
    private final EvidenceRepository evidenceRepository;
    private final ClaimMapper claimMapper;

    @Override
    @Transactional
    public ClaimResponseDTO initiateClaim(ClaimRequestDTO request) {
        Policy policy = policyRepository.findById(request.getPolicyID())
                .orElseThrow(() -> new NotFoundException("Active Policy not found"));

        Claim claim = new Claim();
        claim.setPolicy(policy);
        claim.setIncidentDate(request.getIncidentDate());
        claim.setReportedDate(LocalDate.now());
        claim.setClaimType(request.getClaimType());
        claim.setDescription(request.getDescription());
        claim.setStatus(ClaimStatus.OPEN);

        return claimMapper.toResponse(claimRepository.save(claim));
    }

//    @Override
//    @Transactional
//    public ClaimAssignmentResponseDTO assignAdjuster(Long claimID, Long adjusterId, String priority) {
//        Claim claim = claimRepository.findById(claimID)
//                .orElseThrow(() -> new NotFoundException("Claim not found"));
//
//        ClaimAssignment assignment = new ClaimAssignment();
//        assignment.setClaim(claim);
//        assignment.setAdjusterID(adjusterId);
//        assignment.setPriority(priority);
//        assignment.setAssignmentDate(LocalDateTime.now());
//
//        ClaimAssignment saved = assignmentRepository.save(assignment);
//
//        // Return a manual DTO or use a specific AssignmentMapper if you have one
//        ClaimAssignmentResponseDTO dto = new ClaimAssignmentResponseDTO();
//        dto.setAssignmentID(saved.getAssignmentID());
//        dto.setClaimID(claimID);
//        dto.setAdjusterID(adjusterId);
//        dto.setPriority(priority);
//        dto.setAssignmentDate(saved.getAssignmentDate());
//        return dto;
//    }
@Override
@Transactional
public ClaimAssignmentResponseDTO assignAdjuster(Long claimID, Long adjusterId, String priority) {
    // We override the incoming adjusterId to always be 5 (Anusha)
    Long dedicatedAdjusterId = 5L;

    Claim claim = claimRepository.findById(claimID)
            .orElseThrow(() -> new NotFoundException("Claim not found"));

    // Check if already assigned to avoid duplicates
    List<ClaimAssignment> existing = assignmentRepository.findByClaim_ClaimID(claimID);
    ClaimAssignment assignment = existing.isEmpty() ? new ClaimAssignment() : existing.get(0);

    assignment.setClaim(claim);
    assignment.setAdjusterID(dedicatedAdjusterId); // Hardcoded to Anusha
    assignment.setPriority(priority != null ? priority : "MEDIUM");
    assignment.setAssignmentDate(LocalDateTime.now());

    ClaimAssignment saved = assignmentRepository.save(assignment);

    // Return response
    ClaimAssignmentResponseDTO dto = new ClaimAssignmentResponseDTO();
    dto.setAssignmentID(saved.getAssignmentID());
    dto.setClaimID(claimID);
    dto.setAdjusterID(dedicatedAdjusterId);
    dto.setPriority(saved.getPriority());
    dto.setAssignmentDate(saved.getAssignmentDate());

    return dto;
}

    @Override
    @Transactional
    public ReserveResponseDTO setClaimReserve(Long claimID, Double estimatedRepairCost) {
        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new NotFoundException("Claim not found"));

        // AUTO formula: (Repair - Deductible) + 10% Fee
        Double deductible = 500.0;
        Double netLoss = estimatedRepairCost - deductible;
        Double reserveAmount = netLoss + (estimatedRepairCost * 0.10);

        Reserve reserve = new Reserve();
        reserve.setClaim(claim);
        reserve.setAmount(Math.max(0.0, reserveAmount));
        reserve.setSetDate(LocalDate.now());
        reserve.setStatus("Open");

        return claimMapper.toReserveResponse(reserveRepository.save(reserve));
    }

    @Override
    @Transactional
    public ReserveResponseDTO updateReserves(Long claimID, Long adjusterId, Double amount) {
        // In this context, the controller uses 'amount' as the final calculated reserve
        return setClaimReserve(claimID, amount + 500.0); // Adjusting back to trigger the internal logic
    }

    @Override
    @Transactional
    public SettlementResponseDTO settleClaim(Long claimID) {
        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new NotFoundException("Claim not found"));

        List<Reserve> reserves = reserveRepository.findByClaim_ClaimID(claimID);
        if (reserves.isEmpty()) throw new NotFoundException("No reserve found for claim: " + claimID);
        Reserve reserve = reserves.get(0);

        Settlement settlement = new Settlement();
        settlement.setClaim(claim);
        settlement.setSettlementAmount(reserve.getAmount());
        settlement.setSettlementDate(LocalDateTime.now());
        settlement.setStatus(PaymentStatus.PAID);

        claim.setStatus(ClaimStatus.SETTLED);
        reserve.setStatus("Released");

        claimRepository.save(claim);
        reserveRepository.save(reserve);
        return claimMapper.toSettlementResponse(settlementRepository.save(settlement));
    }

    @Override
    @Transactional
    public EvidenceResponseDTO addEvidence(Long claimID, EvidenceRequestDTO request) {
        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new NotFoundException("Claim not found"));

        Evidence evidence = new Evidence();
        evidence.setClaim(claim);
        evidence.setType(request.getType());
        evidence.setUri(request.getUri());
        evidence.setUploadedDate(LocalDateTime.now());

        Evidence saved = evidenceRepository.save(evidence);

        EvidenceResponseDTO dto = new EvidenceResponseDTO();
        dto.setEvidenceID(saved.getEvidenceID());
        dto.setClaimID(claimID);
        dto.setType(saved.getType());
        dto.setUri(saved.getUri());
        dto.setUploadedDate(saved.getUploadedDate());
        return dto;
    }

    @Override
    public List<ClaimResponseDTO> getAssignedQueue(Long adjusterId) {
        // If we want to be strict, we can force the lookup for ID 5 here too
        return assignmentRepository.findByAdjusterIDOrderByPriorityDesc(5L).stream()
                .map(assignment -> claimMapper.toResponse(assignment.getClaim()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimResponseDTO> listByCustomer(Long customerId) {
        return claimRepository.findByPolicy_Customer_CustomerID(customerId).stream()
                .map(claimMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Object getGlobalSummary() {
        return Map.of(
                "totalClaims", claimRepository.count(),
                "openClaims", claimRepository.countByStatus(ClaimStatus.OPEN),
                "settledClaims", claimRepository.countByStatus(ClaimStatus.SETTLED)
        );
    }

    @Override
    @Transactional
    public void updateClaimPriority(Long claimID, String priority) {
        List<ClaimAssignment> assignments = assignmentRepository.findByClaim_ClaimID(claimID);
        if (!assignments.isEmpty()) {
            ClaimAssignment a = assignments.get(0);
            a.setPriority(priority);
            assignmentRepository.save(a);
        }
    }
}