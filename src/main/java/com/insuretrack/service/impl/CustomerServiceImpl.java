package com.insuretrack.service.impl;

import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.mapper.CustomerModuleMapper;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerModuleMapper mapper;

    @Override
    @Transactional
    public CustomerResponseDTO createCustomerProfile(CustomerRequestDTO dto) {
        Customer customer = mapper.toEntity(dto);

        // Crucial: Set parent reference for JPA cascading
        if (customer.getBeneficiaries() != null) {
            customer.getBeneficiaries().forEach(b -> b.setCustomer(customer));
        }
        if (customer.getInsuredObjects() != null) {
            customer.getInsuredObjects().forEach(obj -> obj.setCustomer(customer));
        }

        Customer saved = customerRepository.save(customer);
        return mapper.toResponse(saved);
    }

    @Override
    public CustomerResponseDTO getCustomerProfile(Long id) {
        return customerRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerResponseDTO> getAllProfiles() {
        return customerRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteProfile(Long id) {
        customerRepository.deleteById(id);
    }
}
