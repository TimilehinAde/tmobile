package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.CreateUserRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.UserLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreateUserResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.UserLoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassengerServiceImplTest {
    @Autowired
    private PassengerService passengerService;

    private CreateUserRequest createUserRequest;
    private UserLoginRequest userLoginRequest;


@BeforeEach
    void setUp(){
    createUserRequest = new CreateUserRequest();
    createUserRequest.setEmailAddress("akintunde55@gmail.com");
    createUserRequest.setPhoneNumber("07037393008");
    createUserRequest.setFirstNumber("Akiin");
    createUserRequest.setLastNumber("Tunnde");
    createUserRequest.setPassword("32#Dfdhyght");

}
@Test
    void test_ThatUserCanBeCreated(){
    CreateUserResponse createUserResponse = passengerService.userSignUp(createUserRequest);
    assertNotNull(createUserResponse);
    assertEquals("sign up successful", createUserResponse.getMessage());
}
@Test
    void test_ThatUserCanLogin(){
    userLoginRequest = new UserLoginRequest();
    userLoginRequest.setPassword("32#Dfdhyght");
    userLoginRequest.setPhoneNumber("07037393008");

    UserLoginResponse userLoginResponse = passengerService.login(userLoginRequest);
    assertNotNull(userLoginResponse);
    assertEquals("successful logged in", userLoginResponse.getMessage());

}

}