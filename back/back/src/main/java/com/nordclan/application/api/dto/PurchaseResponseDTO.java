package com.nordclan.application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "purchase", title = "BuyResponseDTO", description = "Ответ на покупку")
public class PurchaseResponseDTO extends BaseResponseDTO {

    @Schema(title = "discountAmount", description = "Сумма скидки")
    private BigDecimal discountAmount;
    @Schema(title = "finalDiscount", description = "Размер скидки")
    private BigDecimal finalDiscount;
}
