package com.ust.ExpenseTracker.service;

import com.ust.ExpenseTracker.entity.UserEntity;
import com.ust.ExpenseTracker.repository.UserRepository;
import com.ust.ExpenseTracker.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class CustomUserDetailService implements UserDetailsService       {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
         UserEntity user= userRepository.findByEmailId(emailId);
        return new CustomUserDetails(user);
    }
}
