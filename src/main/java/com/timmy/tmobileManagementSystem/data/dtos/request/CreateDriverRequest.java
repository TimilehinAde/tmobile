package com.timmy.tmobileManagementSystem.data.dtos.request;

import lombok.Data;


@Data
public class CreateDriverRequest extends CreateRequest {

    private String driversLicense;
}
