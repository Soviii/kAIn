package com.example.users.controller;

import com.example.users.dto.CreateUserResponseDTO;
import com.example.users.dto.LoginUserResponseDTO;
import com.example.users.dto.CreateUserRequestDTO;
import com.example.users.service.UserService;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public CreateUserResponseDTO registerUser(@Valid @RequestBody CreateUserRequestDTO user) {
        CreateUserResponseDTO newUser = this.userService.createUser(user);
        return newUser;
    }

    /**
     * Handles user login requests via HTTP GET with credentials passed in request headers.
     *
     * Expected headers:
     * 
     *   email - The user's email address (required).
     *   password - The user's password (required).
     * 
     *
     * Validates the provided credentials and returns a {@link LoginUserResponseDTO}
     * containing the user's ID and a generated JWT token if authentication is successful.
     *
     * @param email    the user's email address from the "email" request header
     * @param password the user's password from the "password" request header
     * @return {@link ResponseEntity} containing {@link LoginUserResponseDTO} on success
     */
    @GetMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(
            @Valid @RequestHeader("email") String email,
            @RequestHeader("password") String password) {

        LoginUserResponseDTO userIdAndJWT = this.userService.validateUserCredentials(email, password);

        return ResponseEntity.ok(userIdAndJWT);
    }
}
