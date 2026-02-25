package com.insuretrack.service.impl;

import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
import com.insuretrack.exception.NotFoundException;
import com.insuretrack.mapper.CustomerMapper;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Replaces @Autowired for better testability
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CustomerResponseDTO createCustomerProfile(CustomerRequestDTO dto) {
        // 1. Map DTO to Entity
        Customer customer = mapper.toEntity(dto);

        // 2. Link the User Account
        // We set the User object. JPA automatically maps the user_id foreign key.
        if (dto.getEmail() != null) {
            User existingUser = userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new NotFoundException("No User account found with email: " + dto.getEmail()));

            customer.setUser(existingUser);
            // Removed customer.setUserID() - the relationship is handled via customer.setUser(existingUser)
        }

        // 3. Sync nested collections (Beneficiaries & Assets)
        // This ensures the foreign key (customer_id) is set in the child tables
        if (customer.getBeneficiaries() != null) {
            customer.getBeneficiaries().forEach(b -> b.setCustomer(customer));
        }
        if (customer.getInsuredObjects() != null) {
            customer.getInsuredObjects().forEach(obj -> obj.setCustomer(customer));
        }

        // 4. Save and return mapped Response
        Customer saved = customerRepository.save(customer);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerProfile(Long id) {
        return customerRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Customer profile not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllProfiles() {
        return customerRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProfile(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Cannot delete. Profile not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}