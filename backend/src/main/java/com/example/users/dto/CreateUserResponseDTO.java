package com.example.users.dto;

public class CreateUserResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long id;

    // necessary for certain JPA functionalities
    public CreateUserResponseDTO(){}

    public CreateUserResponseDTO (String firstName, String lastName, String email, String password, Long id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){ 
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
