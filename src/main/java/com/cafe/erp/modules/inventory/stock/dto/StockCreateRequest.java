package com.cafe.erp.modules.inventory.stock.dto;
import com.cafe.erp.common.enums.StockStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class StockCreateRequest {
    private Long materialId;
    private Long locationId;
    private String uomCode;
    private String batchNo;
    private LocalDate expiryDate;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private StockStatus stockStatus;
}

