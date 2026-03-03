package com.cafe.erp.modules.inventory.stock.entity;

import com.cafe.erp.common.enums.StockStatus;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    private String batchNo;

    private LocalDate expiryDate;

    @Column(name = "uom_code", nullable = false)
    private String uomCode;

    @Column(precision = 18, scale = 6)
    private BigDecimal quantity;

    @Column(precision = 18, scale = 6)
    private BigDecimal unitCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status")
    private StockStatus stockStatus;
}

