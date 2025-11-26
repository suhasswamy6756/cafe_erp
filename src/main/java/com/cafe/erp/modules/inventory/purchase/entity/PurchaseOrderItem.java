package com.cafe.erp.modules.inventory.purchase.entity;


import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "po_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;

    private String uomCode;

    @Column(precision = 18, scale = 6)
    private BigDecimal orderedQty;

    @Column(precision = 18, scale = 6)
    private BigDecimal unitPrice;

    @Column(precision = 18, scale = 6)
    private BigDecimal taxPercent;

}
