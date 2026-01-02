package com.cafe.erp.modules.inventory.stock_adjusments.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockAdjustmentListDTO {
    private Long adjustmentId;
    private String materialName;
    private String adjustmentType; // INCREASE/DECREASE
    private BigDecimal quantity;
    private LocalDateTime adjustedAt;
}
