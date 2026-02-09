package com.insuretrack.controller;

import com.insuretrack.dto.LoginRequest;
import com.insuretrack.entity.User;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.repository.AuditLogRepository; // Or an AuditService
import com.insuretrack.service.AuditService;
import com.insuretrack.service.impl.AuditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Inject the AuditService interface here
    @Autowired
    private AuditService auditService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

                // 2. Call the method on the instance, not the class
                auditService.logAction(user, "LOGIN_SUCCESS", "AUTH_MODULE", "User authenticated via manual login");

                return ResponseEntity.ok("Login Successful! Role: " + user.getRole());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}