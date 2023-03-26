package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;


@Data
public class DriverCreateRequest extends CreateRequest {

    private String driversLicense;
}
