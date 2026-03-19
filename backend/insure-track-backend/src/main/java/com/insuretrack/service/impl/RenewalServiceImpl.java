package com.insuretrack.service.impl;

import com.insuretrack.dto.CreateRenewalOfferRequest;
import com.insuretrack.dto.RenewalRequestDTO;
import com.insuretrack.dto.RenewalResponseDTO;
import com.insuretrack.entity.Policy;
import com.insuretrack.entity.Renewal;
import com.insuretrack.entity.enums.PolicyStatus;
import com.insuretrack.entity.enums.RenewalStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.repository.PolicyRepository;
import com.insuretrack.repository.RenewalRepository;
import com.insuretrack.service.RenewalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RenewalServiceImpl implements RenewalService {

    private final RenewalRepository renewalRepository;
    private final PolicyRepository policyRepository;

    @Override
    public RenewalResponseDTO offer(CreateRenewalOfferRequest req) {
        Policy p = policyRepository.findById(req.policyID)
                .orElseThrow(() -> new NotFoundException("Policy not found: " + req.policyID));

        if (p.getStatus() != PolicyStatus.Active && p.getStatus() != PolicyStatus.Lapsed) {
            throw new IllegalStateException("Only Active or Lapsed policies can be offered renewal.");
        }

        Renewal r = new Renewal();
        r.setPolicyID(p.getPolicyID());
        r.setProposedPremium(req.proposedPremium);
        r.setProposedCoveragesJSON(req.proposedCoveragesJSON);
        r.setOfferDate(req.offerDate);
        r.setStatus(RenewalStatus.Offered);

        return toResponse(renewalRepository.save(r));
    }

    @Override
    public RenewalResponseDTO decide(RenewalRequestDTO req) {
        Renewal r = renewalRepository.findById(req.renewalID)
                .orElseThrow(() -> new NotFoundException("Renewal not found: " + req.renewalID));

        if (r.getStatus() != RenewalStatus.Offered) {
            throw new IllegalStateException("Renewal must be in Offered status.");
        }

        if (Boolean.TRUE.equals(req.accept)) {
            r.setStatus(RenewalStatus.Accepted);
            Policy p = policyRepository.findById(r.getPolicyID())
                    .orElseThrow(() -> new NotFoundException("Policy not found: " + r.getPolicyID()));
            p.setStatus(PolicyStatus.Active);
            p.setPremium(r.getProposedPremium());
        } else {
            r.setStatus(RenewalStatus.Declined);
        }

        return toResponse(r);
    }

    @Override
    public List<RenewalResponseDTO> listByPolicy(Long policyID) {
        return renewalRepository.findByPolicyID(policyID)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private RenewalResponseDTO toResponse(Renewal r) {
        RenewalResponseDTO dto = new RenewalResponseDTO();
        dto.renewalID = r.getRenewalID();
        dto.policyID = r.getPolicyID();
        dto.proposedPremium = r.getProposedPremium();
        dto.proposedCoveragesJSON = r.getProposedCoveragesJSON();
        dto.offerDate = r.getOfferDate();
        dto.status = r.getStatus();
        return dto;
    }
}