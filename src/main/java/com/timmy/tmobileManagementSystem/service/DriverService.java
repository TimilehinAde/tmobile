package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.LoginResponse;

public interface DriverService {
     CreateDriverResponse driverSignUp(CreateDriverRequest createDriver);
     LoginResponse login(LoginRequest driverLoginRequest);
}
