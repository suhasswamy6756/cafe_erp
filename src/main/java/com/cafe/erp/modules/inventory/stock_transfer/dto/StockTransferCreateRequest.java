package com.cafe.erp.modules.inventory.stock_transfer.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StockTransferCreateRequest {
    private Long fromLocationId;
    private Long toLocationId;
    private String remarks;
    private List<StockTransferItemDTO> items;
}
