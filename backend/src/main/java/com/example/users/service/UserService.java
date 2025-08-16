package com.example.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.config.JwtConfig;
import com.example.users.dto.CreateUserRequestDTO;
import com.example.users.dto.CreateUserResponseDTO;
import com.example.users.dto.LoginUserResponseDTO;
import com.example.users.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;


import com.example.users.model.User;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;

    public UserService (UserRepository userRepository, JwtConfig jwtConfig){
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
    }

    @Transactional
    public CreateUserResponseDTO createUser(CreateUserRequestDTO user, HttpServletResponse response){
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        
        List<User> existingUsers = userRepository.findByEmail(email);

        if(existingUsers.size()>0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists.");
        }

        User newUser = new User(firstName, lastName, email, password);
        userRepository.save(newUser);
        Long id = newUser.getId(); // must save user first before getting the id; JPA convention

        // prepare jwt with httpOnly
        String token = createToken(Long.toString(newUser.getId()));
        jwtConfig.addCookie(token, response);

        CreateUserResponseDTO newUserResponse = new CreateUserResponseDTO(firstName, lastName, email, password, id, token);

        return newUserResponse;
    }


    /**
     * Validates a user's email and password against stored credentials.
     *
     * If the credentials match an existing user, returns a LoginUserResponseDTO
     * containing the user's ID and a generated JWT token.
     * If no match is found, throws a ResponseStatusException with HTTP 401 (Unauthorized).
     *
     * @param email    the user's email address
     * @param password the user's password
     * @return a LoginUserResponseDTO with the user's ID and a JWT token
     * @throws ResponseStatusException if the credentials are invalid
     * 
     * TODO: implement JWT if server side sessions isn't manageable within timeframe
     */
    @Transactional(readOnly = true)
    public LoginUserResponseDTO validateUserCredentials(String email, String password, HttpServletResponse response) {
        Optional<User> maybeUser = userRepository.findByEmailAndPassword(email, password);
        User existingUser;

        if (maybeUser.isPresent()) {
            existingUser = maybeUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
        }

        // prepare jwt with httpOnly
        String token = createToken(Long.toString(existingUser.getId()));
        jwtConfig.addCookie(token, response);
        LoginUserResponseDTO userIdAndJWT = new LoginUserResponseDTO(existingUser.getId(), token);

        return userIdAndJWT;
    }

    private String createToken(String userId) {
        return jwtConfig.generateToken(userId);
    }
}