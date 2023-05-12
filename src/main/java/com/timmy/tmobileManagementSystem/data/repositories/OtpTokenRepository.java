package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.OtpToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public  interface OtpTokenRepository extends MongoRepository<OtpToken, String> {
    Optional<OtpToken> findByToken(String token);
    void deleteConfirmationTokensByExpiredAtBefore(LocalDateTime current);

    String confirmAt(LocalDateTime now, String confirmationToken);
}