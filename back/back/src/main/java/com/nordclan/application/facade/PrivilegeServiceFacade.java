package com.nordclan.application.facade;

import com.nordclan.application.api.dto.CurrentDiscountResponseDTO;
import com.nordclan.application.api.dto.PurchaseResponseDTO;
import com.nordclan.application.api.dto.RefundResponseDTO;
import com.nordclan.application.api.dto.TransactionRequestDTO;
import com.nordclan.application.api.dto.level.LevelResponseDTO;
import com.nordclan.application.exception.IllegalPurchaseSumForRefund;
import com.nordclan.application.model.entity.Card;
import com.nordclan.application.model.entity.Level;
import com.nordclan.application.model.enums.LevelType;
import com.nordclan.application.service.CardService;
import com.nordclan.application.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceFacade {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);
    private static final List<String> WEEKDAYS = List.of("MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY");

    private final CardService cardService;
    private final LevelService levelService;

    public PurchaseResponseDTO processPurchase(TransactionRequestDTO request) {
        Card card = findCard(request.getCardNumber());

        BigDecimal currentPurchasesSum =
                requireNonNullElse(card.getTotalPurchasesSum(), ZERO);
        BigDecimal purchaseAmount = request.getPurchaseAmount();
        BigDecimal totalPurchasesSum = currentPurchasesSum.add(purchaseAmount);
        card.setTotalPurchasesSum(totalPurchasesSum);

        Level level = card.getLevel();
        if (totalPurchasesSum.compareTo(level.getMaxLimit()) > 0) {
            level = defineNewLevel(totalPurchasesSum);
            card.setLevel(level);
        }
        cardService.update(card);

        BigDecimal discount = calculateDiscount(purchaseAmount, level);
        BigDecimal discountedSum = purchaseAmount.subtract(discount);

        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setDiscountAmount(discountedSum);
        response.setFinalDiscount(discount);
        return response;
    }

    private static BigDecimal calculateDiscount(BigDecimal purchaseAmount,
                                                Level level) {
        BigDecimal discount = level.getDiscount();
        BigDecimal finalDiscount = discount;
        if (purchaseAmount.compareTo(HUNDRED) <= 0 && !level.getType().equals(LevelType.PLATINUM)) {
            finalDiscount = ZERO;
            return finalDiscount;
        }
        if (checkDiscountOfDay(LocalDate.now())) {

            BigDecimal additionalDiscount = level.getExtraDiscount();
            finalDiscount = discount.add(additionalDiscount);
        }

        return purchaseAmount.multiply(finalDiscount)
                .divide(HUNDRED);
    }

    private Level defineNewLevel(BigDecimal total) {
        return levelService.findByPurchaseSumBetweenLimits(total);
    }

    public RefundResponseDTO processRefund(TransactionRequestDTO request) {
        Card card = findCard(request.getCardNumber());

        BigDecimal currentPurchasesSum =
                requireNonNullElse(card.getTotalPurchasesSum(), ZERO);
        BigDecimal refundPurchaseSum = request.getPurchaseAmount();
        BigDecimal totalPurchasesSum =
                currentPurchasesSum.subtract(refundPurchaseSum);

        if (isNegative(totalPurchasesSum)) {
            throw new IllegalPurchaseSumForRefund();
        }

        card.setTotalPurchasesSum(totalPurchasesSum);

        Level level = card.getLevel();
        BigDecimal levelMinLimit = level.getMinLimit();

        if (totalPurchasesSum.compareTo(levelMinLimit) < 0) {
            Level newLevel = defineNewLevel(totalPurchasesSum);
            card.setLevel(newLevel);
        }

        cardService.update(card);

        RefundResponseDTO response = new RefundResponseDTO();
        response.setRefundAmount(refundPurchaseSum);

        return response;
    }

    private static boolean isNegative(BigDecimal value) {
        return value.signum() == -1;
    }

    @Transactional(readOnly = true)
    public LevelResponseDTO getCurrentLevel(Long cardNumber) {
        Card card = findCard(cardNumber);
        Level level = card.getLevel();
        return LevelResponseDTO.builder()
                .level(level.getType().getValue())
                .discount(level.getDiscount())
                .build();
    }

    @Transactional(readOnly = true)
    public CurrentDiscountResponseDTO getCurrentDiscount(Long cardNumber,
                                                         LocalDate date) {
        Card card = findCard(cardNumber);
        CurrentDiscountResponseDTO response = new CurrentDiscountResponseDTO();
        BigDecimal discountByDay = ZERO;
        Level level = card.getLevel();
        if (checkDiscountOfDay(date)) {
            discountByDay = level.getExtraDiscount();
        }

        response.setDiscountByCard(level.getDiscount());
        response.setDiscountOfDay(discountByDay);
        return response;
    }

    private Card findCard(Long cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    private static boolean checkDiscountOfDay(LocalDate date) {
        String day = String.valueOf(date.getDayOfWeek());
        return WEEKDAYS.contains(day);
    }
}
