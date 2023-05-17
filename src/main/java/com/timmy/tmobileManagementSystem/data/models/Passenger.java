package com.timmy.tmobileManagementSystem.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Passenger extends Person{
    @Id
    private String id;

    @DBRef
    private Set<Card> userCards = new HashSet<>();
    //private ArrayList<Integer> currentPosition = new ArrayList<>();
    private List<Payment> paymentList = new ArrayList<>();

}
