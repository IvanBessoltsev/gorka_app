package com.nordclan.application.api.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "CreateCardDTO", description = "Создание новой карты")
public class CreateCardDTO {

    @Schema(title = "cardNumber", description = "Номер карты")
    private Long cardNumber;
}
