package com.timmy.tmobileManagementSystem.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
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
}
