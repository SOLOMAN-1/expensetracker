package com.ust.ExpenseTracker.service;

import com.ust.ExpenseTracker.entity.LogInDto;
import com.ust.ExpenseTracker.entity.UserEntity;
import com.ust.ExpenseTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ExpenseService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    public String register(UserEntity user) {
        String password=user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "User registered successfully..You can now log in with your email id as username" +
                " and password.";
    }

    public String verify(LogInDto logInDto) {
       Authentication authenticate= authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken
                (logInDto.getEmailId(),logInDto.getPassword()));
//        UserEntity user=userRepository.findByEmailId(logInDto.getEmailId());
        if(authenticate.isAuthenticated()){
            return jwtService.getToken(logInDto);
        }
        return "Login failure..";
    }
}
