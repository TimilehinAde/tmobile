package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.models.OtpToken;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class OtpTokenServiceImpl implements OtpTokenService {
    @Autowired
    private OtpTokenRepository otpTokenRepository;
    @Autowired
    private DriverService driverService;

    @Autowired
    private PassengerService passengerService;



    @Override
    public String verifyOtp(VerifyOtpRequest verifyOtpRequest) {

        OtpToken foundToken = otpTokenRepository.findByToken(verifyOtpRequest.getToken())
                .orElseThrow(() -> new RuntimeException("token doesn't exist"));
        if (foundToken.getConfirmAt() != null)
            throw new RuntimeException("Token has been used");
        if (foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");
        foundToken.setConfirmAt(LocalDateTime.now());
        driverService.enableDriver(foundToken.getDriver());
        otpTokenRepository.save(foundToken);
        return "verified";
    }

    public String verifyPassengerOtp(VerifyOtpRequest verifyOtpRequest) {

        OtpToken foundToken = otpTokenRepository.findByToken(verifyOtpRequest.getToken())
                .orElseThrow(() -> new RuntimeException("token doesn't exist"));
        if (foundToken.getConfirmAt() != null)
            throw new RuntimeException("Token has been used");
        if (foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");
        foundToken.setConfirmAt(LocalDateTime.now());
        passengerService.enablePassenger(foundToken.getPassenger());
        otpTokenRepository.save(foundToken);
        return "verified";
    }
}

