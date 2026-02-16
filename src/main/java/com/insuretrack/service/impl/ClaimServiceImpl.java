package com.insuretrack.service.impl;

import com.insuretrack.dto.*;
import com.insuretrack.entity.*;
import com.insuretrack.entity.enums.ClaimStatus;
import com.insuretrack.mapper.ClaimMapper;
import com.insuretrack.repository.*;
import com.insuretrack.service.AuditService;
import com.insuretrack.service.ClaimService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaimServiceImpl implements ClaimService {
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private ClaimAssignmentRepository claimAssignmentRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private EvidenceRepository evidenceRepository;
    @Autowired
    private SettlementRepository settlementRepository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private ClaimMapper claimMapper; // Inject your mapper

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public ClaimResponseDTO processIntake(ClaimRequestDTO dto) {
        // 1. Use MapStruct to convert DTO to Entity automatically
        // This handles incidentDate conversion and policy mapping
        Claim claim = claimMapper.toEntity(dto);

        // 2. Set business-logic specific fields
        claim.setReportedDate(LocalDate.now());
        claim.setStatus(ClaimStatus.OPEN);

        Claim savedClaim = claimRepository.save(claim);

        // 3. Log the action using the policy number for context
        auditService.logAction(15L, "FNOL_INTAKE", "CLAIMS_MODULE",
                "Claim opened for Policy: " + savedClaim.getPolicy().getPolicyNumber());

        // 4. Return the Response DTO
        return claimMapper.toResponse(savedClaim);
    }
    @Override
    @Transactional
    public ClaimAssignmentResponseDTO assignAdjuster(Long claimID, Long adjusterID, String priority) {
        Claim claim = claimRepository.findById(claimID).orElseThrow();

        ClaimAssignment assignment = new ClaimAssignment();
        assignment.setClaim(claim);
        assignment.setAdjusterID(adjusterID);
        assignment.setPriority(priority);
        assignment.setAssignmentDate(LocalDateTime.now());

        claim.setStatus(ClaimStatus.INVESTIGATING);
        claimRepository.save(claim);

        ClaimAssignment saved = claimAssignmentRepository.save(assignment);
        auditService.logAction(adjusterID, "CLAIM_ASSIGNMENT", "CLAIMS_MODULE", "Claim #" + claimID + " assigned.");

        // Convert to DTO before returning
        return claimMapper.toAssignmentResponse(saved);
    }

    @Override
    @Transactional
    public ReserveResponseDTO updateReserves(Long claimID, Long adjusterID, Double amount) {
        // 1. Existing Validation (Adjuster check and Claim check)
        User user = userRepository.findById(adjusterID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"ADJUSTER".equalsIgnoreCase(String.valueOf(user.getRole()))) {
            throw new RuntimeException("User is not an authorized Adjuster!");
        }

        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // 2. Find if a reserve already exists for this claim
        // We assume one primary reserve record per claim for this logic
        Reserve reserve = reserveRepository.findByClaim_ClaimID(claimID)
                .stream()
                .findFirst()
                .orElse(new Reserve()); // If not found, create new

        // 3. Update the fields
        if (reserve.getReserveID() == null) {
            reserve.setClaim(claim);
            reserve.setSetDate(LocalDateTime.now());
            reserve.setStatus(com.insuretrack.entity.enums.ClaimStatus.OPEN);
        }

        reserve.setAmount(amount); // When this changes, @UpdateTimestamp kicks in!

        // 4. Save and return
        Reserve savedReserve = reserveRepository.save(reserve);

        auditService.logAction(adjusterID, "RESERVE_UPDATED", "CLAIMS_MODULE", "New amount: " + amount);

        return claimMapper.toReserveResponse(savedReserve);
    }
    @Override
    @Transactional
    public EvidenceResponseDTO addEvidence(Long claimID, EvidenceRequestDTO dto) {
        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // Use Mapper to create entity from DTO
        Evidence evidence = claimMapper.toEvidenceEntity(dto);
        evidence.setClaim(claim);
        evidence.setUploadedDate(LocalDateTime.now());

        Evidence saved = evidenceRepository.save(evidence);

        // Log the action
        auditService.logAction(1L, "EVIDENCE_UPLOADED", "CLAIMS_MODULE", "Evidence added to Claim: " + claimID);

        // Return the Response DTO
        return claimMapper.toEvidenceResponse(saved);
    }
    @Override
    @Transactional
    public SettlementResponseDTO settleClaim(Long claimID, Double amount, String paymentReference) {
        Claim claim = claimRepository.findById(claimID).orElseThrow();
        claim.setStatus(ClaimStatus.SETTLED);

        Settlement settlement = new Settlement();
        settlement.setClaim(claim);
        settlement.setSettlementAmount(amount);
        settlement.setSettlementDate(LocalDateTime.now());
        settlement.setPaymentReference(paymentReference);
        settlement.setStatus(ClaimStatus.SETTLED);

        Settlement saved = settlementRepository.save(settlement);
        return claimMapper.toSettlementResponse(saved);
    }

    @Override
    @Transactional
    public ClaimSummaryDTO getClaimSummary(Long claimID) {
        Claim claim = claimRepository.findById(claimID)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        // Fetch related items
        List<ClaimAssignment> assignments = claimAssignmentRepository.findByClaim_ClaimID(claimID);
        List<Reserve> reserves = reserveRepository.findByClaim_ClaimID(claimID);
        List<Evidence> evidence = evidenceRepository.findByClaim_ClaimID(claimID);
        Settlement settlement = settlementRepository.findByClaim_ClaimID(claimID);

        // Use Mapper to build the full Summary DTO
        return claimMapper.toSummaryDto(claim, assignments, reserves, evidence, settlement);
    }
}
