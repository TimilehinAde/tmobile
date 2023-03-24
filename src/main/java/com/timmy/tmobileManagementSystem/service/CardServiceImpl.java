package com.timmy.tmobileManagementSystem.service;

import com.timmy.tmobileManagementSystem.data.models.Card;
import com.timmy.tmobileManagementSystem.data.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService{

    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void addCard(Card card) {
        cardRepository.save(card);
    }

    public Optional<Card> finByCard(String cardNo) {
        return cardRepository.findByCardNo(cardNo);
    }

    @Override
    public Card findCardById(Card cardId) {
        return cardRepository.findByCardId(cardId);
    }



    @Override
    public void deleteCard(String id) {

    }
}
