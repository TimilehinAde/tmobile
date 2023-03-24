package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceImplTest {

    @Autowired
    private DriverService driverService;
    private CreateDriverRequest createDriverRequest;
    private LoginRequest driverLoginRequest;

    @BeforeEach
    void setUp(){
        createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setEmailAddress("to34pe@gmail.com");
        createDriverRequest.setDriversLicense("343A687Dfd4");
        createDriverRequest.setPassword("T3im$3ffhg4b");
        createDriverRequest.setFirstName("Tope");
        createDriverRequest.setLastName("Harry");
        createDriverRequest.setPhoneNumber("07037175683");
    }

    @Test
    void test_ThatDriverCanBeCreated(){

        CreateDriverResponse createDriverResponse = driverService.driverSignUp(createDriverRequest);
        assertNotNull(createDriverResponse);
        assertEquals("Driver created successfully", createDriverResponse.getMessage());
    }
    @Test
    void test_ThatDriverCanLogin(){
        driverLoginRequest = new LoginRequest();
        driverLoginRequest.setEmailAddress("tope@gmail.com");
        driverLoginRequest.setPassword("T3im$3ffhg4b");
        LoginResponse createLoginResponse = driverService.login(driverLoginRequest);
        assertNotNull(driverLoginRequest);
        assertEquals("login successful", createLoginResponse.getMessage());
    }

}