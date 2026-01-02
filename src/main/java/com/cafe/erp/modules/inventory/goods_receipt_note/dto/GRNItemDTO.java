package com.cafe.erp.modules.inventory.goods_receipt_note.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GRNItemDTO {

    private Long grnMaterialId;
    private Long materialId;
    private String materialName;
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

