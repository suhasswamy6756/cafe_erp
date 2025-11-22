package com.cafe.erp.modules.inventory.stock.entity;


import com.cafe.erp.common.enums.StockStatus;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "stocks",
        indexes = {
                @Index(columnList = "item_id"),
                @Index(columnList = "location_id"),
                @Index(columnList = "batchNo"),
                @Index(columnList = "expiryDate")
        }
)
@Getter
@Setter
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private Boolean isRawMaterial = false;

    @Column(name = "uom_code")
    private String uomCode;

    @Column(precision = 18, scale = 6)
    private BigDecimal quantity;

    private String uom;

    private String batchNo;

    private LocalDate expiryDate;

    @Column(precision = 18, scale = 6)
    private BigDecimal unitCost;

    @Enumerated(EnumType.STRING)

    private StockStatus stockStatus;

    // NEW FOR AUDIT
    private String sourceType;   // PURCHASE / RECEIPT / ADJUSTMENT / TRANSFER / PRODUCTION
    private Long sourceId;
}

