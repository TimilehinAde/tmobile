package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CarRegistrationRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CarRegistrationResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.models.Location;
import jakarta.mail.MessagingException;

public interface DriverService {
     CreateDriverResponse signUp(CreateDriverRequest createDriverRequest) throws MessagingException;
    // String verifyOtp(VerifyOtpRequest verifyOtpRequest);
     void enableDriver(Driver driver);

     //String sendOTP(SendOtpRequest sendOtpRequest) throws MessagingException;

     String login(LoginRequest loginRequest);
     //void createToken(Driver driver, String otpNumber);
     Driver getDriver(String location);
     Car getCarByDriver(Driver assignedDriver);

     CarRegistrationResponse carRegister(CarRegistrationRequest request);
}
