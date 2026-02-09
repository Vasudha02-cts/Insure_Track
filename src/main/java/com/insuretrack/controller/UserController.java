package com.insuretrack.controller;

import com.insuretrack.dto.CustomerDTO;
import com.insuretrack.dto.UserDTO;
import com.insuretrack.entity.Customer;
import com.insuretrack.entity.User;
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

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    //  for Customer registration if you want the profile linked
    @PostMapping("/register/customer")
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        // Create the UserDTO from the incoming CustomerDTO data
        UserDTO userDTO = new UserDTO();
        userDTO.setName(customerDTO.getName());
        userDTO.setEmail(customerDTO.getEmail());
        userDTO.setPhone(customerDTO.getPhone());

        // Pass the populated DTOs to the service
        return ResponseEntity.ok(userService.registerCustomer(userDTO, customerDTO));
    }

    // Feature: Retrieve specific user profile
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // List all users (Admin functionality)
    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}