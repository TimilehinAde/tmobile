package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, String> {
}
