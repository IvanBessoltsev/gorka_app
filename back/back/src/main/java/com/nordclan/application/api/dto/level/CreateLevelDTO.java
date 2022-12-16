package com.nordclan.application.api.dto.level;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(title = "CreateLevelDTO", description = "Создание нового уровня карты")
public class CreateLevelDTO {
    @Schema(title = "type", description = "Тип уровня карты")
    private String type;

    @Schema(title = "discount", description = "Постоянная скидка по карте")
    private BigDecimal discount;

    @Schema(title = "extraDiscount", description = "Дополнительная скидка по карте")
    private BigDecimal extraDiscount;

    @Schema(title = "conditions", description = "Описание дополнительных условий")
    private String conditions;

    @Schema(title = "limit", description = "Лимит суммы для скидки")
    private BigDecimal limit;
}
