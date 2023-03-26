package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.DriverCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.ResponseClass;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import jakarta.mail.MessagingException;

public interface DriverService {
     CreateDriverResponse driverSignUp(DriverCreateRequest createDriver) throws MessagingException;

     ResponseClass login(LoginRequest driverLoginRequest);
     void createToken(Driver driver, String otpNumber);
}
