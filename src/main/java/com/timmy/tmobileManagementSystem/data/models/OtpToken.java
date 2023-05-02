package com.timmy.tmobileManagementSystem.data.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@RequiredArgsConstructor
public class OtpToken {
    @Id
    private String id;
    private String token;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime expiredAt;
    private LocalDateTime confirmAt;
    @DBRef
    private Driver driver;
    @DBRef
    private Passenger passenger;

    public OtpToken(String id, String token, LocalDateTime expiredAt,
                    LocalDateTime confirmAt, Driver driver) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.confirmAt = confirmAt;
        this.driver=driver;
    }

    public OtpToken() {

    }
}
