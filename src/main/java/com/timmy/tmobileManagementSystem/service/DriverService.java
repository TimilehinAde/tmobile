package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.SendOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.ResponseClass;
import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.models.Location;
import jakarta.mail.MessagingException;

public interface DriverService {
     String driverSignUp(CreateDriverRequest createDriver) throws MessagingException;
     void verifyOtp(VerifyOtpRequest verifyOtpRequest);

     //String sendOTP(SendOtpRequest sendOtpRequest) throws MessagingException;

     String login(LoginRequest driverLoginRequest);
     //void createToken(Driver driver, String otpNumber);
     Driver getDriver(Location location);
     Car getCarByDriver(Driver assignedDriver);
}
