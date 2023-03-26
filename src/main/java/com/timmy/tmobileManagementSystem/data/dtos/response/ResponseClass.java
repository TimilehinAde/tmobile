package com.timmy.tmobileManagementSystem.data.dtos.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseClass {
    private String message;
    private HttpStatus statusCode;
}
