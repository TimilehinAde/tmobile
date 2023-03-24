package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String emailAddress;
    private String firstNumber;
    private String lastNumber;
    private String phoneNumber;
    private String password;
}
