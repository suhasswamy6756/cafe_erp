package com.cafe.erp.modules.inventory.stock.dto;


import com.cafe.erp.common.enums.StockStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StockCreateRequest {
    private Long itemId;
    private Long locationId;
    private Boolean isRawMaterial;
    private String uomCode;
    private BigDecimal quantity;
    private String uom;
    private String batchNo;
    private LocalDate expiryDate;
    private BigDecimal unitCost;
    private StockStatus stockStatus;
    private String sourceType;
    private Long sourceId;
}

