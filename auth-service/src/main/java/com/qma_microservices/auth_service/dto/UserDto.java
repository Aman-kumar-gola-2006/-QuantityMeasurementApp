package com.qma_microservices.auth_service.dto;


public class UserDto {
    private String name;
    private String email;

    public UserDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}