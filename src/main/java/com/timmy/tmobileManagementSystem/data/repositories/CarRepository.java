package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {

    Optional<Car> findByDriverId(String id);


    Optional<Car> findByDriver(Driver driver);
}
