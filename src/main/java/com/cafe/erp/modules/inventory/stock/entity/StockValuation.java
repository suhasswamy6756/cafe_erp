package com.cafe.erp.modules.inventory.stock.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.admin.location.entity.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_valuations")
@Getter
@Setter
public class StockValuation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long valuationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(precision = 18, scale = 6)
    private BigDecimal weightedAvgCost;

    @Column(precision = 18, scale = 6)
    private BigDecimal totalValue;

    private LocalDateTime valuationDate;
}

