package com.cafe.erp.modules.inventory.stock_transfer.entity;

import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transfer_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferItem {

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

    private BigDecimal requestedQty;   // User wants

    private BigDecimal issuedQty;      // Deducted from stock at a source

    private BigDecimal shippedQty;     // Optional tracking (same as issued unless damaged in transit)

    private BigDecimal receivedQty;    // Stock added to destination

    private BigDecimal unitCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
}

