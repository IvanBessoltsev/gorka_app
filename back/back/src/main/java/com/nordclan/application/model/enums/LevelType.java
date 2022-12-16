package com.nordclan.application.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LevelType {
    BRONZE("Бронзовая"),
    SILVER("Серебряная"),
    GOLD("Золотая"),
    PLATINUM("Платиновая");

    private final String value;
}
