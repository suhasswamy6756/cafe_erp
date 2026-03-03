package com.cafe.erp.modules.inventory.stock_adjusments.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockAdjustmentResponseDTO {
    private Long adjustmentId;
    private Long materialId;
    private String materialName;
    private String uomCode;
    private String adjustmentType; // INCREASE/DECREASE
    private String reason;
    private BigDecimal quantity;
    private String adjustedBy;
    private LocalDateTime adjustedAt;
}
