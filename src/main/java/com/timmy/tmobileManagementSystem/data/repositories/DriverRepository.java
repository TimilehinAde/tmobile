package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends MongoRepository<Driver, String> {

    Optional<Driver> findByEmail(String email);

    List<Driver> findDriverByLocation(String location);
}
