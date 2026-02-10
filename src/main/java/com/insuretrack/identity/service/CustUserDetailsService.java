package com.insuretrack.identity.service;

import com.insuretrack.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.insuretrack.identity.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustUserDetailsService implements UserDetailsService {
//    @Autowired
    private UserRepository userRepository;
    public CustUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
   @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user=userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole()).build();
    }
}
