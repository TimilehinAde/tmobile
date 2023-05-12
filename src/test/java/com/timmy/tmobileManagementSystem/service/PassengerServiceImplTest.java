package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.dtos.request.BookTripRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.CreatePassengerRequest;
import com.timmy.tmobileManagementSystem.data.dtos.request.PassengerLoginRequest;
import com.timmy.tmobileManagementSystem.data.dtos.response.BookTripResponse;
import com.timmy.tmobileManagementSystem.data.dtos.response.CreatePassengerResponse;
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

    private CreatePassengerRequest createPassengerRequest;
    private PassengerLoginRequest passengerLoginRequest;



@BeforeEach
    void setUp(){
    createPassengerRequest = new CreatePassengerRequest();
    createPassengerRequest.setEmail("johnoe1155@gmail.com");
    createPassengerRequest.setPhoneNumber("07031234582");
    createPassengerRequest.setFirstName("John");
    createPassengerRequest.setLastName("Doe");
    createPassengerRequest.setPassword("JohnDoe1@");

}
@Test
    void test_ThatUserCanBeCreated(){
    CreatePassengerResponse createPassengerResponse = passengerService.passengerSignUp(createPassengerRequest);
    assertNotNull(createPassengerResponse);
    assertEquals("sign up successful", createPassengerResponse.getMessage());
}
@Test
    void test_ThatUserCanLogin(){
    passengerLoginRequest = new PassengerLoginRequest();
    passengerLoginRequest.setPassword("JohnDoe1@");
    passengerLoginRequest.setPhoneNumber("07031234582");

     PassengerLoginResponse passengerLoginResponse = passengerService.login(passengerLoginRequest);
    assertNotNull(passengerLoginResponse);
    assertEquals("successful logged in", passengerLoginResponse.getMessage());
}

@Test
    void test_ThatPassengerCanBookRide(){
    BookTripRequest bookTripRequest = new BookTripRequest();
    bookTripRequest.setEmail("johnoe1155@gmail.com");
    bookTripRequest.setLocation("Mushin");
    bookTripRequest.setDropOffAddress("312 herbert marculay, yaba");
    bookTripRequest.setPickUpAddress("no 13 oluaina street");

    BookTripResponse bookTripResponse = passengerService.bookARide(bookTripRequest);
    assertNotNull(bookTripResponse);
    assertEquals("assigned to a driver", bookTripResponse.getMessage());
}


}