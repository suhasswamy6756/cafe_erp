package com.cafe.erp.modules.inventory.stock.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StockValuationDTO {
    private Long valuationId;
    private Long itemId;
    private Long locationId;
    private BigDecimal weightedAvgCost;
    private BigDecimal totalValue;
    private LocalDateTime valuationDate;
}
