package com.nordclan.application.api.controller;


import com.nordclan.application.api.dto.card.CreateCardDTO;
import com.nordclan.application.api.dto.card.UpdateCardDTO;
import com.nordclan.application.model.entity.Card;
import com.nordclan.application.service.CardService;
import com.nordclan.application.urls.Urls;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping(Urls.Card.FULL)
    @Operation(method = "POST", summary = "Создание новой карты")
    public Card createCard(CreateCardDTO createCardDTO) {
        return cardService.createCard(createCardDTO.getCardNumber());
    }

    @PutMapping(Urls.Card.FULL)
    @Operation(method = "PUT", summary = "Обновление данных карты")
    public Card updateCard(UpdateCardDTO updateCardDTO) {
        return cardService.updateCard(updateCardDTO);
    }

    @GetMapping(Urls.Card.CardNumber.FULL)
    @Operation(method = "GET", summary = "Получение данных о карте")
    public Card getCardByCardNumber(@PathVariable Long cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    @DeleteMapping(Urls.Card.CardNumber.FULL)
    @Operation(method = "DELETE", summary = "Удаление карты")
    public ResponseEntity<String> deleteCardByCardNumber(@PathVariable Long cardNumber) {
        cardService.deleteByCardNumber(cardNumber);
        return new ResponseEntity<>("Карта № " + cardNumber + " успешно удалена", HttpStatus.OK);
    }

}
