package com.timmy.tmobileManagementSystem.utils;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse implements Serializable {
    private ZonedDateTime timeStamp;
    private String status;
    private  Object data;
    private String message;
    private String path;
    private Boolean isSuccessful;
}
