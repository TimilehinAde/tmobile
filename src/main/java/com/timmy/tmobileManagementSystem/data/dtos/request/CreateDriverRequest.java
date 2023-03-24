package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;


@Data
public class CreateDriverRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private String driversLicense;
}
