package com.nordclan.application.repository;

import com.nordclan.application.model.entity.Level;
import com.nordclan.application.model.enums.LevelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("select l from Level l where ?1 between l.minLimit and l.maxLimit")
    Level findByMinLimitLessThanEqualAndMaxLimitGreaterThanEqual(BigDecimal purchaseSum);

    Level findByType(LevelType type);

    void deleteById(Long id);
}
