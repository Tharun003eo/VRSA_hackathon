package com.virtusa.faculty.user.dto;

import lombok.Data;

@Data
public class UserInput {
    private String name;
    private String email;
    private String password;
    private String role;
    private String department;
}