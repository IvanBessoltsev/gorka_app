package com.nordclan.application.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "RefundResponseDTO", description = "Ответ на возврат")
public class RefundResponseDTO extends BaseResponseDTO {

    @Schema(title = "refundAmount", description = "Сумма возврата")
    private BigDecimal refundAmount;
}
