package com.timmy.tmobileManagementSystem.data.models;

import com.timmy.tmobileManagementSystem.data.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Driver extends Person {
    @Id
    private String id;
    private String driversLicense;
    @DBRef
    private Car car;
    private DriverStatus driverStatus;


}
