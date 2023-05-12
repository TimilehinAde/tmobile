package com.timmy.tmobileManagementSystem.service.email;

import jakarta.mail.MessagingException;
import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService  {

   @Async
   void send(String to, String email) throws MessagingException;
    //void sendEmail(String recipientEmail, String name,  String link) throws MessagingException;
    String buildEmail(String name, String link);
}
