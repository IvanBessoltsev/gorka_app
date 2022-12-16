package com.nordclan.application.exception;

import com.nordclan.application.constant.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalPurchaseSumForRefund extends RuntimeException {

    public IllegalPurchaseSumForRefund() {
        super(Errors.Purchase.Refund.ILLEGAL_SUM);
    }
}
