package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;

@Data public class CarRegistrationRequest {
    private String email;
    private String model;
    private String color;
    private String numberPlate;
}
