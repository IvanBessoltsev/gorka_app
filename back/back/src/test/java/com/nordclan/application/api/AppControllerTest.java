package com.nordclan.application.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordclan.application.ApplicationConfig;
import com.nordclan.application.api.dto.CurrentDiscountResponseDTO;
import com.nordclan.application.api.dto.PurchaseResponseDTO;
import com.nordclan.application.api.dto.TransactionRequestDTO;
import com.nordclan.application.model.entity.Card;
import com.nordclan.application.model.entity.Level;
import com.nordclan.application.model.enums.LevelType;
import com.nordclan.application.service.CardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ApplicationConfig.class)
@AutoConfigureMockMvc
public class AppControllerTest {

    private static final List<String> WEEKDAYS = List.of("MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY");

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CardService cardService;

    @Test
    @Sql(value = {"classpath:db/stubs/scripts/common/base_levels_populater.sql",
            "classpath:db/stubs/scripts/AppController__Success.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:db/stubs/scripts/Cleaner.sql")
    public void processingPurchase_success() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        long cardNumber = 123_456_789L;
        request.setCardNumber(cardNumber);
        request.setPurchaseAmount(BigDecimal.valueOf(1500));
        MvcResult result =
                mvc.perform(post("/api/app/purchase")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        PurchaseResponseDTO response = objectMapper.readValue(content, PurchaseResponseDTO.class);
        //assert discounts
        if (checkDiscountOfDay(LocalDate.now())) {
            assertThat(response.getDiscountAmount(), comparesEqualTo(BigDecimal.valueOf(1410.0)));
            assertThat(response.getFinalDiscount(), comparesEqualTo(BigDecimal.valueOf(90.0)));
        } else {
            assertThat(response.getDiscountAmount(), comparesEqualTo(BigDecimal.valueOf(1425.0)));
            assertThat(response.getFinalDiscount(), comparesEqualTo(BigDecimal.valueOf(75.0)));
        }


        //assert that level is upgraded
        Card card = cardService.findByCardNumber(cardNumber);
        Level level = card.getLevel();
        Assertions.assertEquals(level.getType(), LevelType.SILVER);
    }

    @Test
    @Sql(value = {"classpath:db/stubs/scripts/common/base_levels_populater.sql",
            "classpath:db/stubs/scripts/AppController__Success.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:db/stubs/scripts/Cleaner.sql")
    public void processRefund_success() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        long cardNumber = 323_456_789L;
        request.setCardNumber(cardNumber);
        request.setPurchaseAmount(BigDecimal.valueOf(1500));
        mvc.perform(post("/api/app/refund")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        //assert that level is downgraded
        Card card = cardService.findByCardNumber(cardNumber);
        Level level = card.getLevel();
        Assertions.assertEquals(level.getType(), LevelType.GOLD);
        assertThat(card.getTotalPurchasesSum(), comparesEqualTo(BigDecimal.valueOf(4500L)));
    }

    @Test
    @Sql(value = {"classpath:db/stubs/scripts/common/base_levels_populater.sql",
            "classpath:db/stubs/scripts/AppController__Success.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:db/stubs/scripts/Cleaner.sql")
    public void getCurrentLevel_success() throws Exception {
        long cardNumber = 123_456_789;
        mvc.perform(get("/api/app/currentLevel/{cardNumber}", cardNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", is(LevelType.BRONZE.getValue())));
    }

    @Test
    public void getCurrentLevel_fail() throws Exception {
        long cardNumber = 123_456_789;
        mvc.perform(get("/api/app/currentLevel/{cardNumber}", cardNumber))
                .andExpect(status().isNotFound());
    }


    @Test
    @Sql(value = {"classpath:db/stubs/scripts/common/base_levels_populater.sql",
            "classpath:db/stubs/scripts/AppController__Success.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
            value = "classpath:db/stubs/scripts/Cleaner.sql")
    public void getCurrentDiscount_success() throws Exception {
        long cardNumber = 234_456_789;
        MvcResult result = mvc.perform(get("/api/app/discount/{cardNumber}/{date}",
                        cardNumber, LocalDate.of(2022, 12, 15)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        CurrentDiscountResponseDTO response = objectMapper.readValue(contentAsString, CurrentDiscountResponseDTO.class);

        assertThat(response.getDiscountOfDay(), comparesEqualTo(BigDecimal.valueOf(1L)));
        assertThat(response.getDiscountByCard(), comparesEqualTo(BigDecimal.valueOf(5L)));
    }

    @Test
    public void getCurrentDiscount_fail() throws Exception {
        long cardNumber = 123_456_789;
        mvc.perform(get("/api/app/discount/{cardNumber}/{date}",
                        cardNumber, LocalDate.of(2022, 12, 15)))
                .andExpect(status().isNotFound());
    }

    private static boolean checkDiscountOfDay(LocalDate date) {
        String day = String.valueOf(date.getDayOfWeek());
        return WEEKDAYS.contains(day);
    }
}
