package com.drive.EvaluationProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drive.EvaluationProject.DTO.UserRegistrationDto;
import com.drive.EvaluationProject.modal.UserDetails;
import com.drive.EvaluationProject.repository.UserRepository;

@Service
public class UserService {
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails registerUser(UserRegistrationDto userDto) {
        UserDetails user = new UserDetails();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userRepository.save(user);
    }
}
