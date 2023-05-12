package com.timmy.tmobileManagementSystem.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private String location;
    @DBRef
    private Passenger passenger;
    @DBRef
    private Driver driver;
    private int price;
    private LocalDateTime localDateTime = LocalDateTime.now();
}
