package com.nordclan.application.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(title = "TransactionRequestDTO", description = "Запрос на покупку или возврат")
public class TransactionRequestDTO {

    @Schema(title = "cardNumber", description = "Номер карты")
    private Long cardNumber;

    @Schema(title = "operationSum", description = "Сумма операции")
    private BigDecimal purchaseAmount;

}
