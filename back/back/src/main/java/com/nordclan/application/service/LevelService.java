package com.nordclan.application.service;

import com.nordclan.application.api.dto.level.CreateLevelDTO;
import com.nordclan.application.api.dto.level.UpdateLevelDTO;
import com.nordclan.application.constant.Response;
import com.nordclan.application.exception.LevelCardNotFoundException;
import com.nordclan.application.model.entity.Level;
import com.nordclan.application.model.enums.LevelType;
import com.nordclan.application.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    @Transactional
    public ResponseEntity<String> createLevel(CreateLevelDTO levelDTO) {
        Level level = new Level();
        level.setType(LevelType.valueOf(levelDTO.getType()));
        level.setDiscount(levelDTO.getDiscount());
        level.setExtraDiscount(levelDTO.getExtraDiscount());
        level.setMinLimit(levelDTO.getLimit());
        levelRepository.save(level);
        return new ResponseEntity<>(Response.Level.LEVEL_CREATE, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateLevel(UpdateLevelDTO levelDTO) {
        Level level = new Level();
        level.setId(levelDTO.getId());
        level.setType(LevelType.valueOf(levelDTO.getType()));
        level.setDiscount(levelDTO.getDiscount());
        level.setExtraDiscount(levelDTO.getExtraDiscount());
        levelRepository.save(level);
        return new ResponseEntity<>(Response.Level.LEVEL_UPDATE, HttpStatus.OK);
    }

    @Transactional
    public void deleteLevelById(Long id) {
        levelRepository.deleteById(id);
    }

    public Level findLevelById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new LevelCardNotFoundException(id));
    }

    public Level findByType(LevelType levelType) {
        return levelRepository.findByType(levelType);
    }

    public Level findByPurchaseSumBetweenLimits(BigDecimal purchaseSum) {
        return levelRepository.findByMinLimitLessThanEqualAndMaxLimitGreaterThanEqual(purchaseSum);
    }

}
