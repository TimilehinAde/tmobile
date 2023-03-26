package com.timmy.tmobileManagementSystem.data.models;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Location {
    private String locationId;
    private String latitude;
    private String longitude;
}
