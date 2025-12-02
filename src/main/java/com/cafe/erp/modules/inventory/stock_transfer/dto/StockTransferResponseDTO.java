package com.cafe.erp.modules.inventory.stock_transfer.dto;

import com.cafe.erp.common.enums.StockTransferStatus;
import lombok.Data;

import java.util.List;

@Data
public class StockTransferResponseDTO {
    private Long transferId;
    private String transferNumber;
    private Long fromLocationId;
    private Long toLocationId;
    private StockTransferStatus status;
    private List<StockTransferItemDTO> items;
}

