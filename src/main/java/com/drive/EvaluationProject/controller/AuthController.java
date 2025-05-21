package com.drive.EvaluationProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.drive.EvaluationProject.DTO.UserRegistrationDto;
import com.drive.EvaluationProject.modal.UserDetails;
import com.drive.EvaluationProject.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDetails> registerUser(@RequestBody UserRegistrationDto dto) {
        UserDetails user = userService.registerUser(dto);
        return ResponseEntity.ok(user);
    }
}