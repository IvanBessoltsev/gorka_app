package com.nordclan.application.service;


import com.nordclan.application.api.dto.card.UpdateCardDTO;
import com.nordclan.application.exception.CardAlreadyExistsException;
import com.nordclan.application.exception.CardNotFoundException;
import com.nordclan.application.model.entity.Card;
import com.nordclan.application.model.entity.Level;
import com.nordclan.application.model.enums.LevelType;
import com.nordclan.application.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository repository;
    private final LevelService levelService;

    public Card createCard(Long cardNumber) {
        Optional<Card> card = repository.findByNumber(cardNumber);

        if (card.isPresent()) {
            throw new CardAlreadyExistsException(cardNumber);
        } else {
            Level level = levelService.findByType(LevelType.BRONZE);
            Card newCard = new Card();
            newCard.setNumber(cardNumber);
            newCard.setLevel(level);
            newCard.setTotalPurchasesSum(BigDecimal.valueOf(0));
            return repository.save(newCard);
        }
    }

    @Transactional
    public Card updateCard(UpdateCardDTO updateCardDTO) {
        Card card = repository.findById(updateCardDTO.getId())
                .orElseThrow(() -> new CardNotFoundException(updateCardDTO.getCardNumber()));
        card.setNumber(updateCardDTO.getCardNumber());
        card.setTotalPurchasesSum(updateCardDTO.getSumBuy());
        Level level = levelService.findLevelById(updateCardDTO.getLevelId());
        card.setLevel(level);
        repository.save(card);
        return card;
    }

    public void update(Card card) {
        repository.save(card);
    }

    @Transactional
    public Card findByCardNumber(Long cardNumber) {
        return repository.findByNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));
    }

    @Transactional
    public void deleteByCardNumber(Long cardId) {
        repository.deleteByNumber(cardId);
    }


}




