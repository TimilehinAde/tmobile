package com.timmy.tmobileManagementSystem.data.models;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Card {
    @Id
    private String cardId;
    private String passengerId;
    private String cardNo;
    private String CardName;
    private String expireDate;
    private int cvv;
}
