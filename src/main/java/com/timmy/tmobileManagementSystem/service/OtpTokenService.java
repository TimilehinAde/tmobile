package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpTokenService  {
    String verifyOtp(VerifyOtpRequest verifyOtpRequest);

    String verifyPassengerOtp(VerifyOtpRequest verifyOtpRequest);
}
