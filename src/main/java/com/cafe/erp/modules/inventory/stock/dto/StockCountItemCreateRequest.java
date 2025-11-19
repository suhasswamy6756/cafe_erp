package com.cafe.erp.modules.inventory.stock.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockCountItemCreateRequest {
    private Long countId;
    private Long itemId;
    private BigDecimal expectedQty;
    private BigDecimal actualQty;
}
