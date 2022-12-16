package com.nordclan.application.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "number", unique = true, nullable = false)
    private Long number;

    @Column(name = "total_purchases_sum", nullable = false)
    private BigDecimal totalPurchasesSum;

    @OneToOne
    @JoinColumn(name = "level_id")
    private Level level;
}
