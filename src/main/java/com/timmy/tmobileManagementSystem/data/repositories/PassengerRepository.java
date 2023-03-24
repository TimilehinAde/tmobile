package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PassengerRepository extends MongoRepository<Passenger, String> {

    Optional<Passenger> findByEmail(String email);
    Optional<Passenger> findUserByPhoneNumber(String phoneNumber);
}
