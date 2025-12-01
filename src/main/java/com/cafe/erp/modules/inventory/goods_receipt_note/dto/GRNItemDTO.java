package com.cafe.erp.modules.inventory.goods_receipt_note.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GRNItemDTO {

    private Long grnItemId;
    private Long itemId;
    private String itemName;
    private String uomCode;

    private BigDecimal orderedQty;
    private BigDecimal deliveredQty;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;

    private String batchNumber;
    private LocalDate expiryDate;

    private BigDecimal unitCost;
    private BigDecimal totalCost;
}

