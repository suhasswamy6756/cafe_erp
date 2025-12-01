package com.cafe.erp.modules.inventory.goods_receipt_note.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class GRNItemSubmitDTO {
    private Long grnItemId;
    private BigDecimal deliveredQty;
    private BigDecimal acceptedQty;
    private BigDecimal rejectedQty;
    private String batchNo;
    private LocalDate expiryDate;
}


