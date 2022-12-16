package com.nordclan.application.api.dto.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(title = "UpdateCardDTO", description = "Обновить карту")
public class UpdateCardDTO {

    @Schema(title = "id", description = "id карты")
    private Long id;

    @Schema(title = "cardNumber", description = "Номер карты")
    private Long cardNumber;

    @Schema(title = "sumBuy", description = "Сумма покупок по карте")
    private BigDecimal sumBuy;

    @Schema(title = "levelId", description = "Уровень карты")
    private Long levelId;
}
