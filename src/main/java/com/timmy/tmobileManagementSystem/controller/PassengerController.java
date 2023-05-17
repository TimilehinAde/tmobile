package com.timmy.tmobileManagementSystem.controller;

import com.timmy.tmobileManagementSystem.data.dtos.request.*;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.service.OtpTokenService;
import com.timmy.tmobileManagementSystem.service.PassengerService;
import com.timmy.tmobileManagementSystem.utils.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping(path = "/api/v1/passenger")
@CrossOrigin(origins = "*")
@Slf4j
public class PassengerController {
    private final PassengerService passengerService;

    @Autowired
    private OtpTokenService otpTokenService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }
    @PostMapping("/signUp")
    public ResponseEntity<?> passengerSignUp(@RequestBody CreatePassengerRequest request, HttpServletRequest httpServletRequest) throws MessagingException {
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .data(passengerService.passengerSignUp(request))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/verifytoken")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {
        otpTokenService.verifyPassengerOtp(verifyOtpRequest);
        ApiResponse response = ApiResponse.builder()
                .status("Okay")
                .message("Verification is successful")
                .build();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody PassengerLoginRequest passengerLoginRequest, HttpServletRequest httpServletRequest)  {
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .data(passengerService.login(passengerLoginRequest))
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .isSuccessful(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/bookRide")
    public ResponseEntity<?> bookARide(@RequestBody BookTripRequest request, HttpServletRequest httpServletRequest ) {
        log.info("Order a ride request ===> {}", request);
        //BookTripResponse dto = passengerService.bookARide(request);
        ApiResponse response = ApiResponse.builder()
                .status("success")
                .timeStamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI())
                .message("A driver as been found")
                .data(passengerService.bookARide(request))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
