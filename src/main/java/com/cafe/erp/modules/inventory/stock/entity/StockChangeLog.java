package com.cafe.erp.modules.inventory.stock.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_change_logs")
@Getter
@Setter
public class StockChangeLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long changeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    private String changeType;  // CONSUMED, WASTED, ADJUSTMENT, PURCHASE_RECEIPT, TRANSFER

    @Column(precision = 18, scale = 6)
    private BigDecimal oldQuantity;

    @Column(precision = 18, scale = 6)
    private BigDecimal newQuantity;

    @Column(precision = 18, scale = 6)
    private BigDecimal quantityDiff;

    @Column(precision = 18, scale = 6)
    private BigDecimal costPerUom;

    private LocalDateTime changeTimestamp = LocalDateTime.now();
}

