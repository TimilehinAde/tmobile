package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateUserRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.UserLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateUserResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.UserLoginResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerService {

    CreateUserResponse userSignUp(CreateUserRequest createUserRequest);
    UserLoginResponse login(UserLoginRequest loginUserRequest);
}
