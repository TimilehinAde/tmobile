package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerCreateResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import org.springframework.stereotype.Service;


public interface PassengerService {

    PassengerCreateResponse userSignUp(PassengerCreateRequest passengerCreateRequest);
    PassengerLoginResponse login(PassengerLoginRequest passengerLoginRequest);
}