package com.nordclan.application.api.controller;


import com.nordclan.application.api.dto.CurrentDiscountResponseDTO;
import com.nordclan.application.api.dto.PurchaseResponseDTO;
import com.nordclan.application.api.dto.RefundResponseDTO;
import com.nordclan.application.api.dto.TransactionRequestDTO;
import com.nordclan.application.api.dto.level.LevelResponseDTO;
import com.nordclan.application.facade.PrivilegeServiceFacade;
import com.nordclan.application.urls.Urls;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppController {

    private final PrivilegeServiceFacade processingService;

    @PostMapping(Urls.Purchase.FULL)
    @Operation(method = "POST", summary = "Покупка")
    public ResponseEntity<PurchaseResponseDTO> processingPurchase(
            @RequestBody TransactionRequestDTO request) {
        return ResponseEntity.ok(processingService.processPurchase(request));
    }

    @PostMapping(Urls.Refund.FULL)
    @Operation(method = "POST", summary = "Возврат")
    public RefundResponseDTO processingRefund(
            @RequestBody TransactionRequestDTO request) {
        return processingService.processRefund(request);
    }

    @GetMapping(Urls.CurrentLevel.CardNumber.FULL)
    @Operation(method = "GET", summary = "Текущий уровень карты")
    @Parameter(name = "cardNumber", description = "Номер карты лояльности")
    public LevelResponseDTO level(@PathVariable Long cardNumber) {
        return processingService.getCurrentLevel(cardNumber);
    }

    @GetMapping(Urls.Discount.CardNumber.Date.FULL)
    @Operation(method = "GET", summary = "Текущий размер скидки")
    @Parameters({
            @Parameter(name = "cardNumber", description = "Номер карты лояльности"),
            @Parameter(name = "date", description = "Дата")
    })
    public CurrentDiscountResponseDTO discount(@PathVariable Long cardNumber, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return processingService.getCurrentDiscount(cardNumber, date);
    }

}
