package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerCreateRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerCreateResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassengerServiceImplTest {
    @Autowired
    private PassengerService passengerService;

    private PassengerCreateRequest passengerCreateRequest;
    private PassengerLoginRequest passengerLoginRequest;



@BeforeEach
    void setUp(){
    passengerCreateRequest = new PassengerCreateRequest();
    passengerCreateRequest.setEmailAddress("titi55@gmail.com");
    passengerCreateRequest.setPhoneNumber("08169522465");
    passengerCreateRequest.setFirstName("Titi");
    passengerCreateRequest.setLastName("Deborah");
    passengerCreateRequest.setPassword("TitiDeborah1@");

}
@Test
    void test_ThatUserCanBeCreated(){
    PassengerCreateResponse passengerCreateResponse = passengerService.userSignUp(passengerCreateRequest);
    assertNotNull(passengerCreateResponse);
    assertEquals("sign up successful", passengerCreateResponse.getMessage());
}
@Test
    void test_ThatUserCanLogin(){
    passengerLoginRequest = new PassengerLoginRequest();
    passengerLoginRequest.setPassword("TitiDeborah1@");
    passengerLoginRequest.setPhoneNumber("08169522465");

     PassengerLoginResponse passengerLoginResponse = passengerService.login(passengerLoginRequest);
    assertNotNull(passengerLoginResponse);
    assertEquals("successful logged in", passengerLoginResponse.getMessage());

}

}