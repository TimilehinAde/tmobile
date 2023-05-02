package com.timmy.tmobileManagementSystem.data.dtos.request;

import com.timmy.tmobileManagementSystem.data.models.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookTripRequest {
    private String pickUpAddress;
    private String dropOffAddress;
    private String email;
    private Location location;
}
