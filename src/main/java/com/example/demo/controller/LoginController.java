package com.example.demo.controller;

import com.example.demo.entity.ResponseEntity;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(User user){
        return userService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(User user){
        return userService.login(user);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(User user){
        return userService.updatePassword(user);
    }
}
