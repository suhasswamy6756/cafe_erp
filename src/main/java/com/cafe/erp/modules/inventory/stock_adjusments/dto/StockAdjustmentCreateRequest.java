package com.cafe.erp.modules.inventory.stock_adjusments.dto;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class StockAdjustmentCreateRequest {

    private Long materialId;
    private Long locationId;
    private String uomCode;
    private String adjustmentType; // INCREASE/DECREASE
    private String reason;
    private BigDecimal quantity;
    private Long adjustedBy;
}

