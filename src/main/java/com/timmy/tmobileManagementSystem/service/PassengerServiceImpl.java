package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.BookTripRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreatePassengerRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreatePassengerResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import com.timmy.tmobileManagementSystem.data.enums.DriverStatus;
import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.models.Passenger;
import com.timmy.tmobileManagementSystem.data.models.Trip;
import com.timmy.tmobileManagementSystem.data.repositories.PassengerRepository;
import com.timmy.tmobileManagementSystem.data.repositories.TripRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService{

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverService driverService;

    @Autowired
    private TripRepository tripRepository;
    @Override
    public CreatePassengerResponse passengerSignUp(CreatePassengerRequest createPassengerRequest) {
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

        CreatePassengerResponse createPassengerResponse = new CreatePassengerResponse();
        createPassengerResponse.setMessage("sign up successful");

        return createPassengerResponse;
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
    @Override
    public void enablePassenger(Passenger passenger) {
        passenger.setEnable(true);
        passengerRepository.save(passenger);
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
