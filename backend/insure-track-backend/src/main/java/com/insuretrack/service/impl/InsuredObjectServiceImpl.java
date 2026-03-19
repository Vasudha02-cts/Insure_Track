package com.insuretrack.service.impl;

import com.insuretrack.dto.InsuredObjectAgentDTO;
import com.insuretrack.dto.InsuredObjectRequestDTO;
import com.insuretrack.dto.InsuredObjectResponseDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.InsuredObject;
import com.insuretrack.entity.enums.ObjectStatus;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.InsuredObjectMapper;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.repository.InsuredObjectRepository;
import com.insuretrack.service.InsuredObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.insuretrack.entity.enums.InsuredObjectType;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsuredObjectServiceImpl implements InsuredObjectService {

    @Autowired
    private InsuredObjectRepository repository;

    @Autowired
    private InsuredObjectMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public InsuredObjectResponseDTO updateObject(Long objectId, InsuredObjectRequestDTO dto) {
        InsuredObject existing = repository.findById(objectId)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        // Manual update of fields to preserve the existing Customer link
        existing.setValuation(dto.getValuation());
        existing.setRiskScore(dto.getRiskScore());
        existing.setDetailsJSON(dto.getDetailsJSON());
        existing.setStatus(ObjectStatus.valueOf(dto.getStatus()));

        return mapper.toResponse(repository.save(existing));
    }
    @Override
    public InsuredObjectResponseDTO agentCreateObject(InsuredObjectAgentDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerID())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        InsuredObject obj = new InsuredObject();
        obj.setCustomer(customer);
        obj.setDetailsJSON(dto.getDetailsJSON());
        obj.setObjectType(InsuredObjectType.valueOf(dto.getObjectType().toUpperCase()));

        // Agent Inputs
        obj.setValuation(dto.getValuation());
        obj.setRiskScore(dto.getRiskScore());

        // Once Agent valuates, it becomes ACTIVE
        obj.setStatus(ObjectStatus.ACTIVE);

        return mapper.toResponse(repository.save(obj));
    }
    @Override
    public InsuredObjectResponseDTO getObjectById(Long objectId) {
        return repository.findById(objectId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Object not found"));
    }

    @Override
    public List<InsuredObjectResponseDTO> getObjectsByCustomerId(Long customerId) {
        // FIX: Use the specific repository method we discussed earlier
        return repository.findByCustomer_CustomerID(customerId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteObject(Long objectId) {
        repository.deleteById(objectId);
    }
}