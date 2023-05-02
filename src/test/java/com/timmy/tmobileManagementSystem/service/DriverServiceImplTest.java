//package com.timmy.tmobileManagementSystem.service;
//
//import com.timmy.tmobileManagementSystem.data.dtos.request.CreateDriverRequest;
//import com.timmy.tmobileManagementSystem.data.dtos.request.LoginRequest;
//import com.timmy.tmobileManagementSystem.data.dtos.response.CreateDriverResponse;
//import com.timmy.tmobileManagementSystem.data.dtos.response.ResponseClass;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class DriverServiceImplTest {
//
//    @Autowired
//    private DriverService driverService;
//    private CreateDriverRequest createDriverRequest;
//    private LoginRequest driverLoginRequest;
//
//    @BeforeEach
//    void setUp(){
//        createDriverRequest = new CreateDriverRequest();
//        createDriverRequest.setEmailAddress("dotunjohn@gmail.com");
//        createDriverRequest.setDriversLicense("123456789");
//        createDriverRequest.setPassword("password");
//        createDriverRequest.setFirstName("Dotun");
//        createDriverRequest.setLastName("John");
//        createDriverRequest.setPhoneNumber("07012345678");
//    }
//
//    @Test
//    void test_ThatDriverCanBeCreated() throws MessagingException {
//
//        CreateDriverResponse createDriverResponse = driverService.driverSignUp(createDriverRequest);
//        assertNotNull(createDriverResponse);
//        assertEquals("Driver created successfully", createDriverResponse.getMessage());
//    }
//    @Test
//    void test_ThatDriverCanLogin(){
//        driverLoginRequest = new LoginRequest();
//        driverLoginRequest.setEmailAddress("tope@gmail.com");
//        driverLoginRequest.setPassword("T3im$3ffhg4b");
//        ResponseClass createResponseClass = driverService.login(driverLoginRequest);
//        assertNotNull(driverLoginRequest);
//        assertEquals("login successful", createResponseClass.getMessage());
//    }
//
//}