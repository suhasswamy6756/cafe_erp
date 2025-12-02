package com.cafe.erp.modules.inventory.stock_transfer.entity;

import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_transfer_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id")
    private StockTransferHeader transfer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private String uomCode;

    private BigDecimal requestedQty;
    private BigDecimal shippedQty;
    private BigDecimal receivedQty;
    private BigDecimal unitCost;
}

