package com.cafe.erp.modules.inventory.stock_transfer.dto;

import com.cafe.erp.common.enums.StockTransferStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferResponseDTO {
    private Long transferId;
    private String transferNumber;
    private Long fromLocationId;
    private Long toLocationId;
    private String remarks;
    private StockTransferStatus status;
    private List<StockTransferItemDTO> items;
}
