package com.cafe.erp.modules.inventory.stock_transfer.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransferItemCreateDTO {

    @NotNull private Long materialId;
    @NotNull private String uomCode;
    @NotNull private BigDecimal requestedQty;
}
