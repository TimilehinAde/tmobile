package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.LoginResponse;
import com.timmy.tmobileManagementSystem.data.models.Driver;
import com.timmy.tmobileManagementSystem.data.repositories.DriverRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import validators.UserValidators;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepository driverRepository;

    //private ModelMapper modelMapper;
    @Override
    public CreateDriverResponse driverSignUp(CreateDriverRequest createDriver) {
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



        Driver savedDriver = driverRepository.save(driver);

        CreateDriverResponse createDriverResponse = new CreateDriverResponse();

        createDriverResponse.setMessage("Driver created successfully");

        return createDriverResponse;

    }

    @Override
    public LoginResponse login(LoginRequest driverLoginRequest) {
        Driver findDriver = driverRepository.findByEmail(driverLoginRequest.getEmailAddress())
                .orElseThrow(() -> new RuntimeException("email not found"));

        LoginResponse loginResponse = new LoginResponse();
        if (BCrypt.checkpw(driverLoginRequest.getPassword(), findDriver.getPassword())) {
            loginResponse.setMessage("login successful");
            return loginResponse;
        }
        else
            loginResponse.setMessage("re-login");

        return loginResponse;
    }


}



