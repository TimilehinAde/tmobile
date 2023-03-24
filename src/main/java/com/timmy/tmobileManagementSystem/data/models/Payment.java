package com.timmy.tmobileManagementSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document
@Data
public class Payment {
    private String id;
    private String pin;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private Location location;
    @DBRef
    private Passenger passenger;


}
