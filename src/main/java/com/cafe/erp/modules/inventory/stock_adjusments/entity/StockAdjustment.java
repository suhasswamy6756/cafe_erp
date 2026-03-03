package com.cafe.erp.modules.inventory.stock_adjusments.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_adjustments")
@Getter
@Setter
public class StockAdjustment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adjustmentId;

    @Column(name = "material_id", nullable = false)
    private Long materialId;

    @Column(nullable = false)
    private String uomCode;

    @Column(name = "adjustment_type", nullable = false)
    private String adjustmentType; // INCREASE / DECREASE

    private String reason;

    @Column(precision = 18, scale = 6, nullable = false)
    private BigDecimal quantity;

    private String adjustedBy;

    private LocalDateTime adjustedAt = LocalDateTime.now();
}

