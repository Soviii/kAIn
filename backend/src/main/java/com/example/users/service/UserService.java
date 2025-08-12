package com.example.users.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.users.dto.UserRequestDTO;
import com.example.users.dto.UserResponseDTO;
import com.example.users.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.example.users.model.User;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
            
    }
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO user){
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        
        List<User> existingUsers = userRepository.findByEmail(email);

       
        System.out.println(existingUsers.size());
        if(existingUsers.size()>0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists.");
        }

        User newUser = new User(firstName, lastName, email, password);
        userRepository.save(newUser);
        Long id = newUser.getId(); // must save user first before getting the id; JPA convention

        UserResponseDTO newUserResponse = new UserResponseDTO(firstName, lastName, email, password, id);

        return newUserResponse;
    }

    
}

