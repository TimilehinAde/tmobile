package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.models.ConfirmationToken;
import com.timmy.tmobileManagementSystem.data.models.Person;

import java.util.Optional;

public interface ConfirmationTokenService {
        String generateToken(String token);
        void saveConfirmationToken(ConfirmationToken confirmationToken);
        Optional<ConfirmationToken> getConfirmationToken(String token);
        void deleteExpiredToken();
        void setConfirmed(String token);
}

