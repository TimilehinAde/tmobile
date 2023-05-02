package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.models.Passenger;
import com.timmy.tmobileManagementSystem.data.models.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findTripsByDriver(Driver driver);
    List<Trip> findTripsByPassenger(Passenger passenger);
}
