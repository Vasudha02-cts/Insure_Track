package com.insuretrack.controller;

import com.insuretrack.dto.LoginRequestDTO;
import com.insuretrack.dto.LoginResponseDTO;
import com.insuretrack.entity.User;
import com.insuretrack.mapper.UserMapper;
import com.insuretrack.repository.UserRepository;
import com.insuretrack.service.AuditService;
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

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {

                // 2. Call the method on the instance, not the class
                auditService.logAction(user, "LOGIN_SUCCESS", "AUTH_MODULE", "User authenticated via manual login");
                // Map entity to the clean Response DTO
                LoginResponseDTO response = userMapper.toLoginResponse(user);
                response.setMessage("Login Successful!");

                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}