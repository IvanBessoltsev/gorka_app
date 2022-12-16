package com.nordclan.application.model.entity;


import com.nordclan.application.model.enums.LevelType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "level")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type", unique = true)
    @Enumerated(EnumType.STRING)
    private LevelType type;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name= "extra_discount", nullable = false)
    private BigDecimal extraDiscount;

    @Column(name="min_limit")
    private BigDecimal minLimit;

    @Column(name = "max_limit")
    private BigDecimal maxLimit;
}
