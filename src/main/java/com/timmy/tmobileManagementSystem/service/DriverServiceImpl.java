package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.SendOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.ResponseClass;
import com.timmy.tmobileManagementSystem.data.enums.DriverStatus;
import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.Location;
import com.timmy.tmobileManagementSystem.data.models.OtpToken;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.repositories.CarRepository;
import com.timmy.tmobileManagementSystem.data.repositories.CardRepository;
import com.timmy.tmobileManagementSystem.data.repositories.DriverRepository;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import com.timmy.tmobileManagementSystem.service.email.EmailSenderService;
import com.timmy.tmobileManagementSystem.utils.Token;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class DriverServiceImpl implements DriverService {


    @Autowired
    private final DriverRepository driverRepository;
    @Autowired
    private final OtpTokenRepository otpTokenRepository;
    @Autowired
    private CarRepository carRepository;

    private final EmailSenderService emailSenderService;
    private SecureRandom secureRandom = new SecureRandom();

    public DriverServiceImpl(DriverRepository driverRepository, OtpTokenRepository otpTokenRepository, EmailSenderService emailSenderService) {
        this.driverRepository = driverRepository;
        this.otpTokenRepository = otpTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    //private ModelMapper modelMapper;
    @Override
    public String driverSignUp(CreateDriverRequest createDriver) throws MessagingException {
        if (!UserValidators.isValidPassword(createDriver.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", createDriver.getPassword()));
        if (!UserValidators.isValidEmailAddress(createDriver.getEmailAddress()))
            throw new RuntimeException(String.format("%s invalid email", createDriver.getEmailAddress()));
        if (!UserValidators.isValidPhoneNumber(createDriver.getPhoneNumber()))
            throw new RuntimeException(String.format("%s invalid Phone number", createDriver.getPhoneNumber()));
        Driver driver = new Driver();
        if (driverRepository.findByEmail(createDriver.getEmailAddress()).isPresent())
            throw new RuntimeException("email exist");
        else
            driver.setEmail(createDriver.getEmailAddress());

        driver.setDriversLicense(createDriver.getDriversLicense());
        String password = BCrypt.hashpw(createDriver.getPassword(), BCrypt.gensalt());
        driver.setPassword(password);
        driver.setFirstName(createDriver.getFirstName());
        driver.setLastName(createDriver.getLastName());
        driver.setPhoneNumber(createDriver.getPhoneNumber());
        driver.setDriverStatus(DriverStatus.AVAILABLE);

        Driver savedDriver = driverRepository.save(driver);
        //String token = new DecimalFormat("0000").format(new SecureRandom().nextInt(99999));
        OtpToken otpToken = new OtpToken();
        otpToken.setToken(Token.generateToken(8));
        otpToken.setCreatedAt(LocalDateTime.now());
        otpToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        otpToken.setDriver(savedDriver);
        var saveOtpToken =otpTokenRepository.save(otpToken);


        emailSenderService.send(savedDriver.getEmail(), emailSenderService.buildEmail(savedDriver.getFirstName(), String.valueOf(otpToken)));

        return "Token successfully sent to your email. Please check.";
    }

    @Override
    public void verifyOtp(VerifyOtpRequest verifyOtpRequest) {
        OtpToken foundToken = otpTokenRepository.findByToken(verifyOtpRequest.getToken())
                .orElseThrow(() -> new RuntimeException("token doesn't exist"));
        if (foundToken.getConfirmAt() != null)
            throw new RuntimeException("Token has been used");
        if (!Objects.equals(verifyOtpRequest.getToken(), foundToken.getToken()))
            throw new RuntimeException("Incorrect token");
        if (foundToken.getExpiredAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token has expired");
        otpTokenRepository.confirmAt(LocalDateTime.now(), verifyOtpRequest.getToken());
    }

//    private CreateDriverResponse createDriver(Driver newDriver){
//        CreateDriverResponse createDriverResponse = new CreateDriverResponse();
//        createDriverResponse.setMessage("Driver created successfully");
//        emailSenderService.send(newDriver.getEmail(), emailSenderService.buildEmail(newDriver.getFirstName(), token));
//        // createDriverResponse.setToken(createDriverResponse.token);
//
//        return createDriverResponse;
//    }

//    @Override
//    public String sendOTP(SendOtpRequest sendOtpRequest) throws MessagingException {
//        Driver savedDriver = driverRepository.findByEmail(sendOtpRequest.getEmail())
//                .orElseThrow(() -> new RuntimeException("Email not found"));
//        return generateOtpToken(sendOtpRequest, savedDriver);
//    }
    @Override
    public String login(LoginRequest driverLoginRequest) {
        Driver findDriver = driverRepository.findByEmail(driverLoginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email not found"));
        if (!findDriver.isEnable()) return "verify your acc";
        ResponseClass responseClass = new ResponseClass();
        if (BCrypt.checkpw(driverLoginRequest.getPassword(), findDriver.getPassword())) {
            return "successful";
        }
        return "failed";
    }

    @Override
    public Driver getDriver(Location location) {
        List<Driver> drivers = driverRepository.findDriverByLocation(location);
        List<Driver> availableDriver = new ArrayList<>();
        for (Driver driver : drivers) {
            if (driver.getDriverStatus().equals(DriverStatus.AVAILABLE)) {
                availableDriver.add(driver);
            }
        }
        if (!availableDriver.isEmpty()) {
            SecureRandom random = new SecureRandom();
            return drivers.get(random.nextInt(drivers.size()));
        }
        throw new RuntimeException("No driver available at your location");
    }

       @Override
    public Car getCarByDriver(Driver driver) {
        return carRepository.findById(driver).orElseThrow(() -> new RuntimeException("Car not found"));
    }
}

