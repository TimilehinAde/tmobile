package com.timmy.tmobileManagementSystem.controller;

import com.timmy.tmobileManagementSystem.data.dtos.request.DriverCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.service.DriverService;
import com.timmy.tmobileManagementSystem.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/api/v1/Driver")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody DriverCreateRequest driverCreateRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(driverService.driverSignUp(driverCreateRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest)  {
        ApiResponse response = ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(driverService.login(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
