package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.BookTripRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreatePassengerRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.VerifyOtpRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreatePassengerResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.PassengerLoginResponse;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassengerServiceImplTest {
    @Autowired
    private PassengerService passengerService;

    private CreatePassengerRequest createPassengerRequest;
    private PassengerLoginRequest passengerLoginRequest;
    @Autowired
    private OtpTokenService otpTokenService;



@BeforeEach
    void setUp(){
    createPassengerRequest = new CreatePassengerRequest();
    createPassengerRequest.setEmail("pohndoe@gmail.com");
    createPassengerRequest.setPhoneNumber("02031234582");
    createPassengerRequest.setFirstName("John");
    createPassengerRequest.setLastName("Fooe");
    createPassengerRequest.setPassword("Tohndoe1@");
    createPassengerRequest.setLocation("Mushin");

}
@Test
    void test_ThatUserCanBeCreated() throws MessagingException {
    CreatePassengerResponse createPassengerResponse = passengerService.passengerSignUp(createPassengerRequest);
    assertNotNull(createPassengerResponse);
    assertEquals("sign up successful", createPassengerResponse.getMessage());
}

@Test
void test_ThatUserCanVerifyToken(){
    VerifyOtpRequest verifyOtpRequest= new VerifyOtpRequest();
    verifyOtpRequest.setToken("3422");
    otpTokenService.verifyPassengerOtp(verifyOtpRequest);
}
@Test
    void test_ThatUserCanLogin(){
    passengerLoginRequest = new PassengerLoginRequest();
    passengerLoginRequest.setPassword("Tohndoe1@");
    passengerLoginRequest.setPhoneNumber("02031234582");

     PassengerLoginResponse passengerLoginResponse = passengerService.login(passengerLoginRequest);
    assertNotNull(passengerLoginResponse);
    assertEquals("successful logged in", passengerLoginResponse.getMessage());
}

@Test
    void test_ThatPassengerCanBookRide(){
    BookTripRequest bookTripRequest = new BookTripRequest();
    bookTripRequest.setEmail("pohndoe@gmail.com");
    bookTripRequest.setLocation("Mushin");
    bookTripRequest.setDropOffAddress("312 herbert marculay, yaba");
    bookTripRequest.setPickUpAddress("no 13 oluaina street");


    BookTripResponse bookTripResponse = passengerService.bookARide(bookTripRequest);
    assertNotNull(bookTripResponse);
    assertEquals("assigned to a driver", bookTripResponse.getMessage());
}


}