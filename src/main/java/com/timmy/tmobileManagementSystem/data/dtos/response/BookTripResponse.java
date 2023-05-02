package com.timmy.tmobileManagementSystem.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookTripResponse {
    private String message;
    private LocalDateTime dateOfRide;
    private String driverName;
    private String phoneNumber;
    private String model;
    private String numberPlate;
    private String color;
}
