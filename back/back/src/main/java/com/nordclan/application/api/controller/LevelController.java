package com.nordclan.application.api.controller;


import com.nordclan.application.api.dto.level.CreateLevelDTO;
import com.nordclan.application.api.dto.level.UpdateLevelDTO;
import com.nordclan.application.model.entity.Level;
import com.nordclan.application.service.LevelService;
import com.nordclan.application.urls.Urls;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @PostMapping(Urls.Level.FULL)
    @Operation(method = "POST", summary = "Создание нового уровня карты лояльности")
    public ResponseEntity<String> createLevelCard(CreateLevelDTO levelDTO) {
        return levelService.createLevel(levelDTO);
    }

    @PutMapping(Urls.Level.FULL)
    @Operation(method = "PUT", summary = "Обновление уровня лояльности карты")
    public ResponseEntity<String> updateLevelCard(UpdateLevelDTO updateLevelDTO) {
        return levelService.updateLevel(updateLevelDTO);
    }

    @GetMapping(Urls.Level.Id.FULL)
    @Operation(method = "GET", summary = "Получение уровня лояльности карты")
    public Level getLevelCardById(@PathVariable Long id) {
        return levelService.findLevelById(id);
    }

    @DeleteMapping(Urls.Level.Id.FULL)
    @Operation(method = "DELETE", summary = "Удаление уровня лояльности карты")
    public void deleteLevelCard(@PathVariable Long id) {
        levelService.deleteLevelById(id);
    }
}
