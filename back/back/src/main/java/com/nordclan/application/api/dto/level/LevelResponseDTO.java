package com.nordclan.application.api.dto.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
@Schema(title = "LevelResponseDTO", description = "Текущий уровень карты лояльности")
public class LevelResponseDTO {

    @Schema(title = "level", description = "Уровень карты")
    private String level;

    @Schema(title = "sumBuy", description = "Сумма покупок")
    private BigDecimal balance;

    @Schema(title = "discount", description = "Скидка по карте")
    private BigDecimal discount;

    @Schema(title = "conditions", description = "Дополнительные условия")
    private String conditions;
}
