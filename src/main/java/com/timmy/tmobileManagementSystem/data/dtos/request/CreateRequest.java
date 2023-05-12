package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;

@Data
public class CreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
}
