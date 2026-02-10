package com.insuretrack.identity.controller;

import com.insuretrack.identity.entity.User;
import com.insuretrack.identity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/admin")
    public String admin(Authentication auth){
        return "Admin Access Granted"+" Welcome admin "+auth.getName();
    }
    @GetMapping ("/agent")
    public String agent(Authentication auth){
        return "Agent Access Granted"+" Welcome Agent "+auth.getName();
    }
    @GetMapping("/customer")
    public String customer(Authentication auth){
        return "Customer Access Granted"+" Welcome Customer "+auth.getName();
    }

//    //@PreAuthorize("hasAuthority('ADMIN')")
//    public List<User> getUsers(){
//        return userService.getAllUsers();
//    }
}
