package com.timmy.tmobileManagementSystem.utils;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private ZonedDateTime timeStamp;
    private int statusCode;
    private String path;
    private  Object data;
    private Boolean isSuccessful;
}
