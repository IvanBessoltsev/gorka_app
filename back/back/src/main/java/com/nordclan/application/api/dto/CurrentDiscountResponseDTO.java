package com.nordclan.application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(title = "CurrentDiscountRequestDTO", description = "Текущая скидка с учетом даты")
public class CurrentDiscountResponseDTO {

    @Schema(title = "discountByCard", description = "Скидка по карте")
    private BigDecimal discountByCard;

    @Schema(title = "discountOfDay", description = "Скидка по дню недели")
    private BigDecimal discountOfDay;

}
