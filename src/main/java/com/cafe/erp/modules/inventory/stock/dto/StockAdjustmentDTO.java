package com.cafe.erp.modules.inventory.stock.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockAdjustmentDTO {
    private Long adjustmentId;
    private Long itemId;
    private String uomCode;
    private String adjustmentType;
    private String effect;
    private String reason;
    private BigDecimal quantity;
    private Long adjustedBy;
    private LocalDateTime adjustedAt;
}

