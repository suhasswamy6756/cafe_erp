package com.cafe.erp.modules.inventory.stock.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockCountItemDTO {
    private Long id;
    private Long countId;
    private Long itemId;
    private BigDecimal expectedQty;
    private BigDecimal actualQty;
    private BigDecimal variance;
}

