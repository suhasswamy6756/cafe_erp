package com.cafe.erp.modules.inventory.stock.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class StockCountCreateRequest {
    private Long locationId;
    private LocalDate date;
    private Long countedBy;
    private String status;
}
