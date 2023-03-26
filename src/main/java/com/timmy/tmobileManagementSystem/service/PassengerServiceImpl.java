package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerCreateResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import com.timmy.tmobileManagementSystem.data.models.Passenger;
import com.timmy.tmobileManagementSystem.data.repositories.PassengerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerRepository passengerRepository;
    @Override
    public PassengerCreateResponse userSignUp(PassengerCreateRequest passengerCreateRequest) {
        if(!UserValidators.isValidPassword(passengerCreateRequest.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", passengerCreateRequest.getPassword()));
        if(!UserValidators.isValidEmailAddress(passengerCreateRequest.getEmailAddress()))
            throw new RuntimeException(String.format("%s invalid email", passengerCreateRequest.getEmailAddress()));
        if(!UserValidators.isValidPhoneNumber(passengerCreateRequest.getPhoneNumber()))
            throw new RuntimeException(String.format("%s invalid Phone number", passengerCreateRequest.getPhoneNumber()));
        Passenger passenger = new Passenger();
        if (passengerRepository.findByEmail(passengerCreateRequest.getEmailAddress()).isPresent())
            throw new RuntimeException ("Email exist");
        else
            passenger.setEmail(passengerCreateRequest.getEmailAddress());

        passenger.setFirstName(passengerCreateRequest.getFirstName());
        passenger.setLastName(passengerCreateRequest.getLastName());
        if (passengerRepository.findUserByPhoneNumber(passengerCreateRequest.getPhoneNumber()).isPresent())
            throw new RuntimeException("phone number already exists");
        else
            passenger.setPhoneNumber(passengerCreateRequest.getPhoneNumber());
        String password = BCrypt.hashpw(passengerCreateRequest.getPassword(), BCrypt.gensalt());
        passenger.setPassword(password);

        Passenger savedPassenger = passengerRepository.save(passenger);

        PassengerCreateResponse passengerCreateResponse = new PassengerCreateResponse();
        passengerCreateResponse.setMessage("sign up successful");

        return passengerCreateResponse;
    }

    @Override
    public PassengerLoginResponse login(PassengerLoginRequest passengerLoginRequest) {
        Passenger findPassenger = passengerRepository.findUserByPhoneNumber(passengerLoginRequest.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("phone number does not exists"));

        PassengerLoginResponse passengerLoginResponse = new PassengerLoginResponse();
        if (BCrypt.checkpw(passengerLoginRequest.getPassword(), findPassenger.getPassword())) {
            passengerLoginResponse.setMessage("successful logged in");
            return passengerLoginResponse;
        }
        else
            passengerLoginResponse.setMessage("re-login");

        return passengerLoginResponse;
    }

}
