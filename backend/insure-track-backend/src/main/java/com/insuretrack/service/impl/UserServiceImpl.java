package com.insuretrack.service.impl;
import com.insuretrack.dto.CustomerRequestDTO;
import com.insuretrack.dto.CustomerResponseDTO;
import com.insuretrack.dto.UserRequestDTO;
import com.insuretrack.dto.UserResponseDTO;
import com.insuretrack.entity.AuditLog;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
import com.insuretrack.entity.enums.CommonStatus;
import com.insuretrack.entity.enums.UserRole;
import com.insuretrack.mapper.CustomerMapper;
import com.insuretrack.mapper.UserMapper;
import com.insuretrack.repository.AuditLogRepository;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AuditLogRepository auditLogRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (dto.getRole() == UserRole.ADMIN) {
            long adminCount = userRepository.countByRole(UserRole.ADMIN);
            if (adminCount >= 1) {
                throw new RuntimeException("System already has an Admin. Only one Admin allowed.");
            }
        }
        // Use Mapper to go from DTO -> Entity
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        logInternalAction(savedUser, "STAFF_REGISTRATION", "USER_MODULE", "Role: " + savedUser.getRole());

        // Use Mapper to go from Entity -> ResponseDTO
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {
        // 1. Create Identity (User)
        User user = userMapper.toEntityFromCustomerRequest(dto);
        user.setRole(UserRole.CUSTOMER);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User savedUser = userRepository.save(user);

        // 2. Create Profile (Customer)
        Customer customer = customerMapper.toEntity(dto);

        // FIX: Set the User object relationship, NOT setUserID
        customer.setUser(savedUser);
        customer.setStatus(CommonStatus.ACTIVE);

        // Sync child entities
        if (customer.getBeneficiaries() != null) {
            customer.getBeneficiaries().forEach(b -> b.setCustomer(customer));
        }
        if (customer.getInsuredObjects() != null) {
            customer.getInsuredObjects().forEach(obj -> obj.setCustomer(customer));
        }

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponse(savedCustomer);
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