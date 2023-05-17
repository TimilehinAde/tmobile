package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
import com.timmy.tmobileManagementSystem.data.repositories.OtpTokenRepository;
import jakarta.mail.MessagingException;
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
    @Autowired
    private OtpTokenService otpTokenService;


    @BeforeEach
    void setUp(){
        createDriverRequest = new CreateDriverRequest();
        createDriverRequest.setEmail("cuubfonjohn@gmail.com");
        createDriverRequest.setDriversLicense("1234456789");
        createDriverRequest.setPassword("password");
        createDriverRequest.setFirstName("Dootun");
        createDriverRequest.setLastName("Joohn");
        createDriverRequest.setPhoneNumber("07052345678");
        createDriverRequest.setLocation("Mushin");
    }

    @Test
    void test_ThatDriverCanBeCreated() throws MessagingException {

        CreateDriverResponse createDriverResponse = driverService.signUp(createDriverRequest);
        assertNotNull(createDriverResponse);
        assertEquals("driver successfully created", createDriverResponse.getMessage());
    }
    @Test
    void test_ThatOtpIsVerified(){
       VerifyOtpRequest  verifyOtpRequest= new VerifyOtpRequest();
       verifyOtpRequest.setToken("2159");
       otpTokenService.verifyOtp(verifyOtpRequest);

    }
    @Test
    void test_ThatDriverCanLogin(){
       LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("cuubfonjohn@gmail.com");
        loginRequest.setPassword("password");
        String loginResponse =  driverService.login(loginRequest);
        assertNotNull(loginResponse);
        assertEquals("login successful", loginResponse.trim());
    }

}