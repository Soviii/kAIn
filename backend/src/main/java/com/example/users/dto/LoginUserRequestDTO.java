package com.example.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginUserRequestDTO {
    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;

    // necessary for certain JPA functionalities
    public LoginUserRequestDTO() {
    }

    public LoginUserRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
