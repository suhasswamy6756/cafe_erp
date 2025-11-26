package com.cafe.erp.modules.inventory.purchase.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class PurchaseOrderItemDTO {
    private Long poiId;
    private Long materialId;
    private String materialName;
    private String uomCode;
    private BigDecimal orderedQty;
    private BigDecimal unitPrice;
    private BigDecimal taxPercent;
}

