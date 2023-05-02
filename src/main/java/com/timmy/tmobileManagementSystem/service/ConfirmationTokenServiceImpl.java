//package com.timmy.tmobileManagementSystem.service;
//
//import com.timmy.tmobileManagementSystem.data.models.OtpToken;
//import com.timmy.tmobileManagementSystem.data.repositories.ConfirmationTokenRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
//
//    @Autowired
//    public ConfirmationTokenRepository confirmationTokenRepository;
//
//    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
//        this.confirmationTokenRepository = confirmationTokenRepository;
//    }
//
//
////   confirmationTokenRepository @Override
////    public String generateToken(String token) {
////        StringBuilder tok = new StringBuilder();
////        SecureRandom number = new SecureRandom();
////        for (int i = 0; i < 4; i++) {
////            int num = number.nextInt(9);
////            tok.append(num);
////        }
////        StringBuilder token = new StringBuilder(tok.toString());
////        ConfirmationToken confirmationToken = new ConfirmationToken();
////        confirmationToken.setToken(String.valueOf(token));
////        confirmationToken.setCreatedAt(LocalDateTime.now());
////        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
////        confirmationToken.setToken(String.valueOf(token));
////
////        return token.toString();
////        }
//
//    @Override
//    public String generateToken(String token) {
//        return null;
//    }
//
//    public void saveConfirmationToken(OtpToken otpToken) {
//        confirmationTokenRepository.save(otpToken);
//    }
//
//
//    public Optional<OtpToken> getConfirmationToken(String token) {
//        return confirmationTokenRepository.findByToken(token);
//    }
//
//
//    public void deleteExpiredToken() {
//        confirmationTokenRepository.deleteConfirmationTokensByExpiredAtBefore(LocalDateTime.now());
//
//    }
//
//
//    public void setConfirmed(String token) {
//        confirmationTokenRepository.confirmAt(LocalDateTime.now(),token);
//
//    }
//}
