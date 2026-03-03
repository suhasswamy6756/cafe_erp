package com.cafe.erp.modules.inventory.goods_receipt_note.entity;

import com.cafe.erp.common.enums.GRNStatus;
import com.cafe.erp.common.model.BaseEntity;
import com.cafe.erp.modules.inventory.material.entity.Material;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "grn_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GRNItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grnItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grn_id")
    private GRNHeader grnHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Material material;

    private BigDecimal orderedQty;
    private BigDecimal deliveredQty;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;

    private String uomCode;
    private String batchNumber;
    private LocalDate expiryDate;

    private BigDecimal unitCost;
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    private GRNStatus status;
}

