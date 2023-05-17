package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CarRegistrationRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CarRegistrationResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.enums.DriverStatus;
import com.timmy.tmobileManagementSystem.data.models.Car;
import com.timmy.tmobileManagementSystem.data.models.OtpToken;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.repositories.CarRepository;
import com.timmy.tmobileManagementSystem.data.repositories.DriverRepository;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import com.timmy.tmobileManagementSystem.service.email.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class DriverServiceImpl implements DriverService {


    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    OtpTokenRepository otpTokenRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    EmailSenderService emailSenderService;
    // private SecureRandom secureRandom = new SecureRandom();

//    public DriverServiceImpl(DriverRepository driverRepository, OtpTokenRepository otpTokenRepository, EmailSenderService emailSenderService) {
//        this.driverRepository = driverRepository;
//        this.otpTokenRepository = otpTokenRepository;
//        this.emailSenderService = emailSenderService;
//    }

    //private ModelMapper modelMapper;
    @Override
    public CreateDriverResponse signUp(CreateDriverRequest createDriver) throws MessagingException {
        if (!UserValidators.isValidPassword(createDriver.getPassword()))
            throw new RuntimeException(String.format("%s invalid password", createDriver.getPassword()));
        if (!UserValidators.isValidEmailAddress(createDriver.getEmail()))
            throw new RuntimeException(String.format("%s invalid email", createDriver.getEmail()));
        if (!UserValidators.isValidPhoneNumber(createDriver.getPhoneNumber()))
            throw new RuntimeException(String.format("%s invalid Phone number", createDriver.getPhoneNumber()));
        Driver driver = new Driver();
        if (driverRepository.findByEmail(createDriver.getEmail()).isPresent())
            throw new RuntimeException("email exist");
        else
            driver.setEmail(createDriver.getEmail());

        driver.setDriversLicense(createDriver.getDriversLicense());
        String password = BCrypt.hashpw(createDriver.getPassword(), BCrypt.gensalt());
        driver.setPassword(password);
        driver.setFirstName(createDriver.getFirstName());
        driver.setLastName(createDriver.getLastName());
        driver.setPhoneNumber(createDriver.getPhoneNumber());
        driver.setLocation(createDriver.getLocation());
        driver.setDriverStatus(DriverStatus.AVAILABLE);

        Driver savedDriver = driverRepository.save(driver);
        String token = new DecimalFormat("0000").format(new SecureRandom().nextInt(9999));
        OtpToken otpToken = new OtpToken();
        //token.setToken(Token.generateToken(4));
        otpToken.setToken(token);
        otpToken.setCreatedAt(LocalDateTime.now());
        otpToken.setExpiredAt(LocalDateTime.now().plusMinutes(10));
        otpToken.setDriver(savedDriver);
        otpTokenRepository.save(otpToken);
        emailSenderService.send(savedDriver.getEmail(), emailSenderService.buildEmail(savedDriver.getFirstName(),token));

        CreateDriverResponse createDriverResponse = new CreateDriverResponse();
        createDriverResponse.setMessage("driver successfully created");
        createDriverResponse.setStatusCode(201);
        return createDriverResponse;
    }




    @Override
    public void enableDriver(Driver driver) {
        driver.setEnable(true);
        driverRepository.save(driver);
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
    public String login(LoginRequest loginRequest) {
        Driver findDriver = driverRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("email not found"));
        if (!findDriver.isEnable()) return "please verify with otp";
        if (BCrypt.checkpw(loginRequest.getPassword(), findDriver.getPassword()))
                return "login successful";
            return "login failed";

        }



        @Override
        public Driver getDriver (String location){
            List<Driver> drivers = driverRepository.findDriverByLocation(location);
            List<Driver> availableDriver = new ArrayList<>();
            for (Driver driver : drivers) {
                if (driver.getDriverStatus().equals(DriverStatus.AVAILABLE)) {
                    driver.setDriverStatus(DriverStatus.ON_RIDE);
                    availableDriver.add(driver);
                }
            }
            if (!availableDriver.isEmpty()) {
                SecureRandom random = new SecureRandom();
                return drivers.get(random.nextInt(drivers.size()));
            }
            throw new RuntimeException("No available driver");
        }

        @Override
        public Car getCarByDriver (Driver driver){
            return carRepository.findByDriver(driver).orElseThrow(() -> new RuntimeException("Car not found"));
        }

    @Override
    public CarRegistrationResponse carRegister(CarRegistrationRequest request) {
        Optional<Driver> driver =driverRepository.findByEmail(request.getEmail().toLowerCase());
        if (driver.isPresent()){
            Optional<Car> savedCar = carRepository.findByDriverId(driver.get().getId());
            if(savedCar.isEmpty()){
                Car car = new Car();

                car.setModel(request.getModel());
                car.setColor(request.getColor());
                car.setNumberPlate(request.getNumberPlate());
                car.setDriver(driver.get());
                carRepository.save(car);

                CarRegistrationResponse carRegistrationResponse = new CarRegistrationResponse();
                carRegistrationResponse.setMessage("car registered");

                return carRegistrationResponse;
            }
            throw new IllegalStateException("you can't register");
        }
        throw new IllegalStateException("Invalid details");
    }


}
