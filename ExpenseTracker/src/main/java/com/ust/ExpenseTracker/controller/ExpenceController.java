package com.ust.ExpenseTracker.controller;

import com.ust.ExpenseTracker.entity.LogInDto;
import com.ust.ExpenseTracker.entity.UserEntity;
import com.ust.ExpenseTracker.service.CustomUserDetailService;
import com.ust.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenceController {
    @Autowired
    ExpenseService expenseService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String register(@RequestBody UserEntity user){
        return expenseService.register(user);
    }
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String login(@RequestBody LogInDto logInDto){
        return expenseService.verify(logInDto);
    }
}
