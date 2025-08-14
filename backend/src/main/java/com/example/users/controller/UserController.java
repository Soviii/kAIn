package com.example.users.controller;

import com.example.users.dto.CreateUserResponseDTO;
import com.example.users.dto.LoginUserRequestDTO;
import com.example.users.dto.LoginUserResponseDTO;
import com.example.users.dto.CreateUserRequestDTO;
import com.example.users.service.UserService;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend


public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public CreateUserResponseDTO registerUser(@Valid @RequestBody CreateUserRequestDTO user) {
        CreateUserResponseDTO newUser = this.userService.createUser(user);
        return newUser;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@Valid LoginUserRequestDTO req) {
        LoginUserResponseDTO userIdAndJWT = this.userService.validateUserCredentials(req);

        return ResponseEntity.ok(userIdAndJWT);
    }
}
