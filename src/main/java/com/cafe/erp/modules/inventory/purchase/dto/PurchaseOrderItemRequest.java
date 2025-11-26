package com.cafe.erp.modules.inventory.purchase.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseOrderItemRequest {
    private Long materialId;
    private String uomCode;
    private BigDecimal orderedQty;
    private BigDecimal unitPrice;
    private BigDecimal taxPercent;
}

