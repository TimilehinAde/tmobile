package com.timmy.tmobileManagementSystem.data.repositories;

import com.timmy.tmobileManagementSystem.data.models.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<Card, String > {
    Optional<Card> findByCardNo(String cardNo);
    Card findByCardId(Card cardId);
}
