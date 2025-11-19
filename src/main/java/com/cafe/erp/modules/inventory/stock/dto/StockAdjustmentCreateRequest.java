package com.cafe.erp.modules.inventory.stock.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockAdjustmentCreateRequest {
    private Long itemId;
    private String uomCode;
    private String adjustmentType;
    private String effect;
    private String reason;
    private BigDecimal quantity;
    private Long adjustedBy;
}

