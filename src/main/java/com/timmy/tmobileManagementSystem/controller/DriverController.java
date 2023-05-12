package com.timmy.tmobileManagementSystem.controller;

import com.timmy.tmobileManagementSystem.data.dtos.request.CarRegistrationRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.service.DriverService;
import com.timmy.tmobileManagementSystem.service.OtpTokenService;
import com.timmy.tmobileManagementSystem.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/api/v1/Driver")
@CrossOrigin(origins = "*")
public class DriverController {
    private final DriverService driverService;
    @Autowired
    OtpTokenService otpTokenService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody CreateDriverRequest createDriverRequest, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .data(driverService.signUp(createDriverRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/verifytoken")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        otpTokenService.verifyOtp(verifyOtpRequest);
        ApiResponse response = ApiResponse.builder()
                .status("Okay")
                .message("Verification is successful")
                .build();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest)  {
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .data(driverService.login(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/carReg")
    public ResponseEntity<ApiResponse> carRegister(@RequestBody CarRegistrationRequest request, HttpServletRequest httpServletRequest)  {
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .data(driverService.carRegister(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
