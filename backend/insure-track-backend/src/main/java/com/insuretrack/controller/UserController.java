package com.insuretrack.controller;

import com.insuretrack.dto.*;
import com.insuretrack.entity.enums.UserRole;
import com.insuretrack.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.insuretrack.entity.enums.UserRole;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor // Using Lombok instead of Autowired for cleaner code
public class UserController {

    private final UserService userService;

    // 1. Customer Registration (Nested Profile + User)
    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(userService.registerCustomer(dto));
    }

    // 2. Internal Staff Registration (Generic)
    @PostMapping("/register/staff")
    public ResponseEntity<UserResponseDTO> registerStaff(@RequestBody UserRequestDTO dto, @RequestParam UserRole role) {
        // We force the role based on the query parameter for safety
        dto.setRole(role);
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    // 3. Admin: Specific endpoint to create an Underwriter
    @PostMapping("/register/underwriter")
    public ResponseEntity<UserResponseDTO> registerUnderwriter(@RequestBody UserRequestDTO dto) {
        dto.setRole(UserRole.UNDERWRITER);
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}