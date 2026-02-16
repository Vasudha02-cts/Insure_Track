package com.insuretrack.service.impl;

import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.entity.InsuredObject;
import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.mapper.InsuredObjectMapper;
import com.insuretrack.repository.InsuredObjectRepository;
import com.insuretrack.service.InsuredObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InsuredObjectServiceImpl implements InsuredObjectService {

    @Autowired
    private InsuredObjectRepository repository;

    @Autowired
    private InsuredObjectMapper mapper;

    @Override
    @Transactional
    public InsuredObjectResponseDTO updateObject(Long objectId, InsuredObjectRequestDTO dto) {
        InsuredObject existing = repository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        // Manual update of fields to preserve the existing Customer link
        existing.setValuation(dto.getValuation());
        existing.setRiskScore(dto.getRiskScore());
        existing.setDetailsJSON(dto.getDetailsJSON());
        existing.setStatus(CommonStatus.valueOf(dto.getStatus()));

        return mapper.toResponse(repository.save(existing));
    }

    @Override
    public InsuredObjectResponseDTO getObjectById(Long objectId) {
        return repository.findById(objectId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Object not found"));
    }

    @Override
    public List<InsuredObjectResponseDTO> getObjectsByCustomerId(Long customerId) {
        // You'll need to add findByCustomer_CustomerID to your InsuredObjectRepository
        return repository.findAll().stream()
                .filter(obj -> obj.getCustomer().getCustomerID().equals(customerId))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteObject(Long objectId) {
        repository.deleteById(objectId);
    }
}