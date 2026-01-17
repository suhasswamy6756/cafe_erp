package com.cafe.erp.modules.pos.order.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pos_material_consumption")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PosConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consumptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private PosOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private PosOrderItem orderItem;

    private Long materialId;
    private String materialName;

    private BigDecimal usedQty;
    private String uomCode;

}

