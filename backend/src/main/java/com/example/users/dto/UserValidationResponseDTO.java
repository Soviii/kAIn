package com.example.users.dto;

public class UserValidationResponseDTO {

    private String userId;
    private String token;
    private boolean authenticated;

    public UserValidationResponseDTO() {} // JPA required constructor

    public UserValidationResponseDTO(String userId, String token, boolean authenticated) {
        this.userId = userId;
        this.token = token;
        this.authenticated = authenticated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthenticated() {
        return this.authenticated;
    }

    public void setValid(boolean valid) {
        this.authenticated = valid;
    }
}
