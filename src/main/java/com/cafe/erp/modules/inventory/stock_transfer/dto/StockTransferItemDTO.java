package com.cafe.erp.modules.inventory.stock_transfer.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferItemDTO {

    private Long transferItemId;
    private Long materialId;
    private String materialName;
    private String uomCode;
    private BigDecimal issuedQty;
    private BigDecimal requestedQty;
    private BigDecimal shippedQty;
    private BigDecimal receivedQty;
}
