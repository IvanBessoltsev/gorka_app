package com.nordclan.application.exception;

import com.nordclan.application.constant.Errors;

public class CardAlreadyExistsException extends RuntimeException {

    public CardAlreadyExistsException(Long cardNumber) {
        super(String.format(Errors.Card.EXISTS, cardNumber));
    }
}
