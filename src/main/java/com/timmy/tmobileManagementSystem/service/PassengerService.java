package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.BookTripRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreatePassengerRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreatePassengerResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import com.timmy.tmobileManagementSystem.data.models.Passenger;


public interface PassengerService {

    CreatePassengerResponse passengerSignUp(CreatePassengerRequest createPassengerRequest);
    void enablePassenger(Passenger passenger);
    PassengerLoginResponse login(PassengerLoginRequest passengerLoginRequest);
    BookTripResponse bookARide(BookTripRequest request);
    //Car getCarByDriver(Driver assignedDriver);
}