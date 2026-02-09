package com.insuretrack.service.impl;

import com.insuretrack.dto.CustomerDTO;
import com.insuretrack.dto.UserDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
import com.insuretrack.entity.AuditLog;
import com.insuretrack.entity.embeddable.ContactDetails;
import com.insuretrack.entity.enums.CustomerSegment;
import com.insuretrack.entity.enums.UserRole;
import com.insuretrack.repository.CustomerRepository;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.repository.AuditLogRepository;
import com.insuretrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import com.insuretrack.entity.enums.CustomerSegment;

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

    @Override
    @Transactional
    public User registerUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered!");
        }
        // Hash the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 1. Save the User
        User savedUser = userRepository.save(user);

        // 2. Create Audit Log Entry
        AuditLog log = new AuditLog();
        log.setUser(savedUser);
        log.setAction("REGISTER");
        log.setResource("USER_MODULE");
        log.setTimestamp(LocalDateTime.now());
        log.setMetadata("New user registered with role: " + savedUser.getRole());

        auditLogRepository.save(log);

        return userRepository.save(user);
    }
    @Transactional
    public Customer registerCustomer(UserDTO userDTO, CustomerDTO customerDTO) {
        // 1. Create User from UserDTO (Don't leave these null!)
        User user = new User();
        user.setName(userDTO.getName());   // Map from DTO
        user.setEmail(userDTO.getEmail()); // Map from DTO
        user.setPhone(userDTO.getPhone()); // Map from DTO
        user.setRole(UserRole.CUSTOMER);
        user = userRepository.save(user);

        // 2. Map data to the Embeddable
        ContactDetails contact = new ContactDetails();
        contact.setEmail(user.getEmail());
        contact.setPhone(user.getPhone());
        contact.setContactInfo(customerDTO.getContactInfo()); // Map from DTO

        // 3. Create Customer and link everything
        Customer customer = new Customer();
        customer.setUser(user);
        customer.setName(user.getName());
        customer.setDob(customerDTO.getDob()); // Map from DTO
        customer.setSegment(customerDTO.getSegment()); // Map from DTO
        customer.setStatus("ACTIVE");
        customer.setContactDetails(contact);

        return customerRepository.save(customer);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}