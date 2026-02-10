package com.insuretrack.identity.controller;

//import com.insuretrack.identity.dao.UserRequestDAO;
import com.insuretrack.identity.entity.User;
import com.insuretrack.identity.repository.UserRepository;
import com.insuretrack.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //@Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    public AuthController(UserService userService,PasswordEncoder passwordEncoder){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
    }
    @PostMapping("/register")
    public String register(@RequestBody User user){
        //User user=new User();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
        return "User registered successfully";
    }
}
