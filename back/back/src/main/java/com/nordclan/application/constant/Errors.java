package com.nordclan.application.constant;

public interface Errors {

    interface Card {
        String EXISTS = "Карта с номером %s уже существует.";
        String NOT_FOUND = "Карта с номером %s отсутствует.";
    }

    interface Level {
        String NOT_FOUND = "Уровень с id %s отсутствует.";
    }

    interface Purchase {
        interface Refund {
            String ILLEGAL_SUM = "Сумма возврата покупки больше, чем итоговая сумма";
        }
    }
}
