package com.nordclan.application.repository;

import com.nordclan.application.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByNumber(Long cardNumber);

    void deleteByNumber(Long cardNumber);
}
