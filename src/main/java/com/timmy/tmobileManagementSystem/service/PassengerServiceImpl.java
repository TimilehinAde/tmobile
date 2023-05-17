package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.BookTripRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreatePassengerRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreatePassengerResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import com.timmy.tmobileManagementSystem.data.enums.DriverStatus;
import com.timmy.tmobileManagementSystem.data.models.*;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import com.timmy.tmobileManagementSystem.data.repositories.PassengerRepository;
import com.timmy.tmobileManagementSystem.data.repositories.TripRepository;
import com.timmy.tmobileManagementSystem.service.email.EmailSenderService;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverService driverService;
    @Autowired
    OtpTokenRepository otpTokenRepository;
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    private TripRepository tripRepository;
    @Override
    public CreatePassengerResponse passengerSignUp(CreatePassengerRequest createPassengerRequest) throws MessagingException {
        if(!UserValidators.isValidPassword(createPassengerRequest.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", createPassengerRequest.getPassword()));
        if(!UserValidators.isValidEmailAddress(createPassengerRequest.getEmail()))
            throw new RuntimeException(String.format("%s invalid email", createPassengerRequest.getEmail()));
        if(!UserValidators.isValidPhoneNumber(createPassengerRequest.getPhoneNumber()))
            throw new RuntimeException(String.format("%s invalid Phone number", createPassengerRequest.getPhoneNumber()));
        Passenger passenger = new Passenger();
        if (passengerRepository.findByEmail(createPassengerRequest.getEmail()).isPresent())
            throw new RuntimeException ("Email exist");
        else
            passenger.setEmail(createPassengerRequest.getEmail());

        passenger.setFirstName(createPassengerRequest.getFirstName());
        passenger.setLastName(createPassengerRequest.getLastName());
        if (passengerRepository.findUserByPhoneNumber(createPassengerRequest.getPhoneNumber()).isPresent())
            throw new RuntimeException("phone number already exists");
        else
            passenger.setPhoneNumber(createPassengerRequest.getPhoneNumber());
        String password = BCrypt.hashpw(createPassengerRequest.getPassword(), BCrypt.gensalt());
        passenger.setPassword(password);

        Passenger savedPassenger = passengerRepository.save(passenger);
        String token = new DecimalFormat("0000").format(new SecureRandom().nextInt(9999));
        OtpToken otpToken = new OtpToken();
        otpToken.setToken(token);
        otpToken.setCreatedAt(LocalDateTime.now());
        otpToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        otpToken.setPassenger(savedPassenger);
        otpTokenRepository.save(otpToken);
        emailSenderService.send(savedPassenger.getEmail(), emailSenderService.buildEmail(savedPassenger.getFirstName(),token)); ;

        CreatePassengerResponse createPassengerResponse = new CreatePassengerResponse();
        createPassengerResponse.setMessage("sign up successful");
        createPassengerResponse.setStatusCode(201);

        return createPassengerResponse;

    }
    @Override
    public void enablePassenger(Passenger passenger) {
        passenger.setEnable(true);
        passengerRepository.save(passenger);
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

    @Override
    public BookTripResponse bookARide(BookTripRequest request) {
        Optional<Passenger> savedPassenger = passengerRepository.findByEmail(request.getEmail().toLowerCase());
        if (savedPassenger.isPresent()){
            Driver assignedDriver = driverService.getDriver(request.getLocation());
            assignedDriver.setDriverStatus(DriverStatus.AVAILABLE);
            Trip trip = new Trip();
            trip.setPassenger(savedPassenger.get());
            trip.setDriver(assignedDriver);
            trip.setLocation(request.getLocation());

            Trip savedTrip = tripRepository.save(trip);
            Car car = driverService.getCarByDriver(assignedDriver);
            return getBookTripResponse(assignedDriver ,savedTrip, car);
        }
        throw new RuntimeException("passenger does not exist");

    }


    private BookTripResponse getBookTripResponse(Driver assignedDriver, Trip savedTrip, Car car) {;
        return BookTripResponse.builder()
                .message("assigned to a driver")
                .driverName(assignedDriver.getFirstName())
                .dateOfRide(savedTrip.getLocalDateTime())
                .model(car.getModel())
                .phoneNumber(assignedDriver.getPhoneNumber())
                .numberPlate(car.getNumberPlate())
                .color(car.getColor())
                .build();
    }


}
