package com.cafe.erp.modules.inventory.stock_transfer.dto;

import java.math.BigDecimal;

public class StockTransferItemCreateRequest {
    private Long materialId;
    private String uomCode;
    private BigDecimal quantity;
}
