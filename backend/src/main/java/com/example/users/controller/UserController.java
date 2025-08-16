package com.example.users.controller;

import com.example.users.dto.CreateUserResponseDTO;
import com.example.users.dto.LoginUserResponseDTO;
import com.example.config.JwtConfig;
import com.example.users.dto.CreateUserRequestDTO;
import com.example.users.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    private final JwtConfig jwtConfig;

    public UserController(UserService userService, JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/register")
    public CreateUserResponseDTO registerUser(@Valid @RequestBody CreateUserRequestDTO user, HttpServletResponse response) {
        CreateUserResponseDTO newUser = this.userService.createUser(user, response);
        
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
            @RequestHeader("password") String password,
            HttpServletResponse response) {

        LoginUserResponseDTO userIdAndJWT = this.userService.validateUserCredentials(email, password, response);

        return ResponseEntity.ok(userIdAndJWT);
    }

    /**
     * Validates the JWT token stored in the "kAIn-jwt" cookie.
     * 
     * If the token is valid, this endpoint responds with 201 Created and the message
     * "good to go mr/mrs chef!". If the token is still active, then will provide a new JWT to "refresh" the session.
     * 
     * If the token is missing or invalid, a 401 Unauthorized response is returned.
     *
     * @param token   The JWT token extracted from the "kAIn-jwt" cookie (nullable if cookie is missing).
     * @param response The HttpServletResponse, used to attach a refreshed token cookie if needed.
     * @return ResponseEntity with status 201 (Created) and a confirmation message if valid,
     *         otherwise 401 (Unauthorized).
     */
    @GetMapping("/validate")
    public ResponseEntity<String> isTokenValid(@CookieValue(value = "kAIn-jwt", required = false) String token, HttpServletResponse response) {
        jwtConfig.checkAndRefreshTokenIfNeeded(token, response);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .buildAndExpand()
            .toUri();

        return ResponseEntity
            .created(location)
            .body("good to go mr/mrs chef!");
    }

    /**
     * Logs out the user by removing the "kAIn-jwt" cookie.
     * 
     * This is done by sending back a cookie with the same name and
     * Max-Age set to 0, instructing the browser to delete it.
     *
     * @param response HttpServletResponse used to clear the cookie.
     * @return ResponseEntity with a boolean flag `true` when logout is successful.
     */
    @DeleteMapping("/logout")
    public ResponseEntity<Boolean> logoutUser(HttpServletResponse response) {
        jwtConfig.removeJwt(response);
        return ResponseEntity.ok(true);
    }
}