package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.DriverCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.ResponseClass;
import com.timmy.tmobileManagementSystem.data.models.ConfirmationToken;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.repositories.DriverRepository;
import com.timmy.tmobileManagementSystem.service.email.EmailSenderService;
import jakarta.mail.MessagingException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import validators.UserValidators;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class DriverServiceImpl implements DriverService {


    private final DriverRepository driverRepository;

    private final EmailSenderService emailSenderService;
    private SecureRandom secureRandom = new SecureRandom();

    public DriverServiceImpl(DriverRepository driverRepository, EmailSenderService emailSenderService ) {
        this.driverRepository = driverRepository;
        this.emailSenderService = emailSenderService;
    }

    //private ModelMapper modelMapper;
    @Override
    public CreateDriverResponse driverSignUp(DriverCreateRequest createDriver) throws MessagingException {
//        Driver driver = modelMapper.map(createDriver, Driver.class);
//        Driver savedDriver = driverRepository.save(driver);
        // Driver driver = createDriver.setEmail();
        if(!UserValidators.isValidPassword(createDriver.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", createDriver.getPassword()));
        if(!UserValidators.isValidEmailAddress(createDriver.getEmailAddress()))
            throw new RuntimeException(String.format("%s invalid email", createDriver.getEmailAddress()));
        if(!UserValidators.isValidPhoneNumber(createDriver.getPhoneNumber()))
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
        Driver savedDriver=driverRepository.save(driver);
        String token = generateToken();
        createToken(savedDriver,token);


        CreateDriverResponse createDriverResponse = new CreateDriverResponse();
        createDriverResponse.setMessage("Driver created successfully");
        emailSenderService.send(savedDriver.getEmail(), emailSenderService.buildEmail(savedDriver.getEmail(), token));
       // createDriverResponse.setToken(createDriverResponse.token);

       return createDriverResponse;

    }

    private String generateToken() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(secureRandom.nextInt(9));
        }
        return stringBuilder.toString();
    }


    @Override
    public ResponseClass login(LoginRequest driverLoginRequest) {
        Driver findDriver = driverRepository.findByEmail(driverLoginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email not found"));

        ResponseClass responseClass = new ResponseClass();
        if (BCrypt.checkpw(driverLoginRequest.getPassword(), findDriver.getPassword())) {
            responseClass.setMessage("login successful");
            return responseClass;
        }
        else
            responseClass.setMessage("re-login");

        return responseClass;
    }


    public void createToken(Driver driver, String otpNumber) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(otpNumber);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setConfirmAt(LocalDateTime.now());
        confirmationToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        confirmationToken.setDriver(driver);
        driverRepository.save(driver);
    }



}



