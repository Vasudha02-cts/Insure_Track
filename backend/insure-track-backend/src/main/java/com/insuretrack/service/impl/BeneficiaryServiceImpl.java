package com.insuretrack.service.impl;

import com.insuretrack.dto.BeneficiaryRequestDTO;
import com.insuretrack.dto.BeneficiaryResponseDTO;
import com.insuretrack.entity.Beneficiary;
import com.insuretrack.mapper.BeneficiaryMapper;
import com.insuretrack.repository.BeneficiaryRepository;
import com.insuretrack.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private BeneficiaryMapper beneficiaryMapper;

    @Override
    @Transactional
    public BeneficiaryResponseDTO updateBeneficiary(Long id, BeneficiaryRequestDTO dto) {
        Beneficiary existing = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Beneficiary not found"));

        existing.setName(dto.getName());
        existing.setRelationship(dto.getRelationship());
        existing.setPercentageShare(dto.getPercentageShare());

        return beneficiaryMapper.toResponse(beneficiaryRepository.save(existing));
    }

    @Override
    public List<BeneficiaryResponseDTO> getBeneficiariesByCustomerId(Long customerId) {
        // Requires findByCustomer_CustomerID in BeneficiaryRepository
        return beneficiaryRepository.findByCustomer_CustomerID(customerId).stream()
                .map(beneficiaryMapper::toResponse)
                .toList();
    }

    @Override
    public void removeBeneficiary(Long id) {
        beneficiaryRepository.deleteById(id);
    }
}