package com.cafe.erp.modules.inventory.stock.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockChangeLogDTO {
    private Long changeId;
    private Long itemId;
    private Long locationId;
    private String changeType;
    private BigDecimal oldQuantity;
    private BigDecimal newQuantity;
    private BigDecimal quantityDiff;
    private BigDecimal costPerUom;
    private LocalDateTime changeTimestamp;
}

