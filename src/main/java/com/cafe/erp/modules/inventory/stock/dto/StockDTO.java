package com.cafe.erp.modules.inventory.stock.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StockDTO {
    private Long stockId;
    private Long materialId;
    private String materialName;
    private Long locationId;
    private String locationName;
    private String uomCode;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private String batchNo;
    private LocalDate expiryDate;
    private String stockStatus;
}
