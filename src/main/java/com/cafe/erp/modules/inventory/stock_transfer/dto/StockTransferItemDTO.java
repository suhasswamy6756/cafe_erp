package com.cafe.erp.modules.inventory.stock_transfer.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StockTransferItemDTO {
    private Long transferItemId;
    private Long materialId;
    private String materialName;
    private String uomCode;
    private BigDecimal requestedQty;
    private BigDecimal shippedQty;
    private BigDecimal receivedQty;
}

