package com.nordclan.application.exception;

import com.nordclan.application.constant.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(Long cardNumber) {
        super(String.format(Errors.Card.NOT_FOUND, cardNumber));
    }

}
