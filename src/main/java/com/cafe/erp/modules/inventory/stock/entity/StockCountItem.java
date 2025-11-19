package com.cafe.erp.modules.inventory.stock.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "stocks_count_items")
@Getter
@Setter
public class StockCountItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "count_id")
    private StockCount count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(precision = 18, scale = 6)
    private BigDecimal expectedQty;

    @Column(precision = 18, scale = 6)
    private BigDecimal actualQty;

    @Column(precision = 18, scale = 6)
    private BigDecimal variance;
}

