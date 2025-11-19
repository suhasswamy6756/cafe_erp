package com.cafe.erp.modules.inventory.stock.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.item.entity.Item;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String uomCode;

    private String adjustmentType; // ADD, REDUCE

    private String effect; // POSITIVE, NEGATIVE

    private String reason; // Wastage, breakage, expiry, manual correction

    @Column(precision = 18, scale = 6)
    private BigDecimal quantity;

    private Long adjustedBy;

    private LocalDateTime adjustedAt = LocalDateTime.now();
}

