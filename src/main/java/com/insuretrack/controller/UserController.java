package com.insuretrack.controller;

import com.insuretrack.dto.*;
import com.insuretrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Standard User Registration
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    // 2. Customer Registration (Profile + User Account)
    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO customerRequest) {
        // 1. Give it a clean variable name
        // 2. Pass the DTO to the service
        CustomerResponseDTO registeredCustomer = userService.registerCustomer(customerRequest);

        // 3. Return the actual data instead of a 'new' empty object
        return ResponseEntity.ok(registeredCustomer);
    }

    // 3. Retrieve specific user profile
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // 4. List all users (Admin functionality)
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}