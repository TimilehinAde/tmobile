package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateUserRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.UserLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateUserResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.UserLoginResponse;
import com.timmy.tmobileManagementSystem.data.models.Passenger;
import com.timmy.tmobileManagementSystem.data.repositories.PassengerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

@Service
public class PassengerServiceImpl implements PassengerService {


    @Autowired
    private PassengerRepository passengerRepository;
    @Override
    public CreateUserResponse userSignUp(CreateUserRequest createUserRequest) {
        if(!UserValidators.isValidPassword(createUserRequest.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", createUserRequest.getPassword()));
        if(!UserValidators.isValidEmailAddress(createUserRequest.getEmailAddress()))
            throw new RuntimeException(String.format("%s invalid email", createUserRequest.getEmailAddress()));
        if(!UserValidators.isValidPhoneNumber(createUserRequest.getPhoneNumber()))
            throw new RuntimeException(String.format("%s invalid Phone number", createUserRequest.getPhoneNumber()));
        Passenger passenger = new Passenger();
        if (passengerRepository.findByEmail(createUserRequest.getEmailAddress()).isPresent())
            throw new RuntimeException ("Email exist");
        else
            passenger.setEmail(createUserRequest.getEmailAddress());

        passenger.setFirstName(createUserRequest.getFirstNumber());
        passenger.setLastName(createUserRequest.getLastNumber());
        if (passengerRepository.findUserByPhoneNumber(createUserRequest.getPhoneNumber()).isPresent())
            throw new RuntimeException("phone number already exists");
        else
            passenger.setPhoneNumber(createUserRequest.getPhoneNumber());
        String password = BCrypt.hashpw(createUserRequest.getPassword(), BCrypt.gensalt());
        passenger.setPassword(password);

        Passenger savedPassenger = passengerRepository.save(passenger);

        CreateUserResponse createUserResponse = new CreateUserResponse();
        createUserResponse.setMessage("sign up successful");

        return createUserResponse;


    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginUserRequest) {
        Passenger findPassenger = passengerRepository.findUserByPhoneNumber(loginUserRequest.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("phone number does not exists"));

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        if (BCrypt.checkpw(loginUserRequest.getPassword(), findPassenger.getPassword())) {
            userLoginResponse.setMessage("successful logged in");
            return userLoginResponse;
        }
        else
            userLoginResponse.setMessage("re-login");

        return userLoginResponse;
    }



}
