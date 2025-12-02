package com.cafe.erp.modules.inventory.stock_transfer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferCreateRequest {

    @NotNull(message = "From Location is required")
    private Long fromLocationId;

    @NotNull(message = "To Location is required")
    private Long toLocationId;

    private String remarks;

    @NotNull(message = "Transfer items are required")
    private List<StockTransferItemCreateDTO> items;
}
