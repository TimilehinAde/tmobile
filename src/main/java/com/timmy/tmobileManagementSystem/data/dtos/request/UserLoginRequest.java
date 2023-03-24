package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String phoneNumber;
    private String password;

}
