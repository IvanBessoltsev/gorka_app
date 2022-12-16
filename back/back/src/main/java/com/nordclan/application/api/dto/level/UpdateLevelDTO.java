package com.nordclan.application.api.dto.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(title = "UpdateLevelDTO", description = "Создание нового уровня карты")
public class UpdateLevelDTO {

    @Schema(title = "id", description = "id уровня карты")
    private Long id;

    @Schema(title = "type", description = "Тип уровня карты")
    private String type;

    @Schema(title = "discount", description = "Постоянная скидка по карте")
    private BigDecimal discount;

    @Schema(title = "extraDiscount", description = "Дополнительная скидка по карте")
    private BigDecimal extraDiscount;

    @Schema(title = "conditions", description = "Описание дополнительных условий")
    private String conditions;
}
