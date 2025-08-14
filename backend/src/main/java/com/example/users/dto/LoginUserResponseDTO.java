package com.example.users.dto;

public class LoginUserResponseDTO {
    private Long userId;
    private String token;

    // necessary for certain JPA functionalities
    public LoginUserResponseDTO() {}

    public LoginUserResponseDTO(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setTokne(String token) {
        this.token = token;
    }
}
