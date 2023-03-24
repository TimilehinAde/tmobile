package com.timmy.tmobileManagementSystem.service;


import com.timmy.tmobileManagementSystem.data.models.Card;

import java.util.Optional;


public interface CardService {
    void addCard(Card card);
    Optional<Card> finByCard(String cardNo);
    Card findCardById(Card card);
    void deleteCard(String id);
}
