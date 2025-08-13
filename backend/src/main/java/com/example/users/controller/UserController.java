package com.example.users.controller;

import com.example.users.dto.UserResponseDTO;
import com.example.users.dto.UserRequestDTO;
import com.example.users.service.UserService;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend


public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    
    public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO newUser = this.userService.createUser(user);
        
        return newUser;

    }
}
