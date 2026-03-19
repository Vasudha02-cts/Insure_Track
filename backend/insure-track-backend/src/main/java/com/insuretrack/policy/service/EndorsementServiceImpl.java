package com.insuretrack.policy.service;

import com.insuretrack.common.enums.ChangeType;
import com.insuretrack.common.enums.EndorsementStatus;
import com.insuretrack.policy.dto.EndorsementRequestDTO;
import com.insuretrack.policy.dto.EndorsementResponseDTO;
import com.insuretrack.policy.entity.Endorsement;
import com.insuretrack.policy.entity.Policy;
import com.insuretrack.policy.repository.EndorsementRepository;
import com.insuretrack.policy.repository.PolicyRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional
public class EndorsementServiceImpl implements EndorsementService {
    private final EndorsementRepository endorsementRepository;
    private final PolicyRepository policyRepository;

    @Override
    public EndorsementResponseDTO create(EndorsementRequestDTO requestDTO) {
        Policy policy=policyRepository.findById(requestDTO.getPolicyId()).orElseThrow(()->new RuntimeException("Policy not found"));
        Endorsement endorsement=Endorsement.builder()
                .policy(policy)
                .changeType(ChangeType.valueOf(requestDTO.getChangeType()))
                .premiumDelta(requestDTO.getPremiumDelta())
                .effectiveDate(LocalDate.now())
                .status(EndorsementStatus.PENDING)
                .build();
        endorsementRepository.save(endorsement);
        return EndorsementResponseDTO.builder()
                .endorsementId(endorsement.getEnodrsementId())
                .policyId(policy.getPolicyId())
                .changeType(endorsement.getChangeType().name())
                .premiumData(endorsement.getPremiumDelta())
                .effectiveDate(endorsement.getEffectiveDate())
                .status(endorsement.getStatus().name())
                .build();
    }
}
