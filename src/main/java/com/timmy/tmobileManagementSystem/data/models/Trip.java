package com.timmy.tmobileManagementSystem.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Trip {
    @Id
    private String id;
    private String pickUpAddress;
    private String dropOffAddress;
    private Location location;
    private Passenger passenger;
    private Driver driver;
    private int price;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
