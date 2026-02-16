package com.insuretrack.service.impl;

import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.dto.UserRequestDTO;
import com.insuretrack.dto.UserResponseDTO;
import com.insuretrack.entity.AuditLog;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
import com.insuretrack.entity.embeddable.ContactDetails;
import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.UserRole;
import com.insuretrack.mapper.CustomerMapper;
import com.insuretrack.mapper.UserMapper;
import com.insuretrack.repository.AuditLogRepository;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }

        // Use Mapper to convert DTO to Entity [cite: 1]
        User user = userMapper.toEntity(userRequestDTO);

        // Hash the password for security
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        // Create Audit Log Entry [cite: 39, 48]
        logInternalAction(savedUser, "REGISTER", "USER_MODULE",
                "New user registered with role: " + savedUser.getRole());

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {
        // 1. Create and save the User (Security Account)
        User user = userMapper.toEntityFromCustomerRequest(dto);
        user.setRole(UserRole.CUSTOMER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        // 2. Map the DTO to Customer Entity
        Customer customer = customerMapper.toEntity(dto);
        customer.setUserID(savedUser.getUserID()); // Link the Long ID
        customer.setStatus(com.insuretrack.entity.enums.CommonStatus.ACTIVE);

        // 3. Setup parent-child links for Module 2 data
        if (customer.getBeneficiaries() != null) {
            customer.getBeneficiaries().forEach(b -> b.setCustomer(customer));
        }
        if (customer.getInsuredObjects() != null) {
            customer.getInsuredObjects().forEach(io -> io.setCustomer(customer));
        }

        // 4. Save the Customer Profile
        Customer savedCustomer = customerRepository.save(customer);

        // 5. Combine data into the final Response
        CustomerResponseDTO response = customerMapper.toResponse(savedCustomer);
        response.setEmail(savedUser.getEmail()); // Add from the User entity
        response.setPhone(savedUser.getPhone()); // Add from the User entity

        logInternalAction(savedUser, "REGISTER_CUSTOMER", "CUSTOMER_MODULE", "Full profile created");

        return response;
    }
    @Override
    public UserResponseDTO getUserById(Long id) {
        // Find entity and map to Response DTO [cite: 42]
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        // Retrieve all and convert the entire list to Response DTOs [cite: 302]
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Internal helper to handle audit logging consistently [cite: 48, 258]
     */
    private void logInternalAction(User user, String action, String resource, String metadata) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setResource(resource);
        log.setTimestamp(LocalDateTime.now());
        log.setMetadata(metadata);
        auditLogRepository.save(log);
    }
}