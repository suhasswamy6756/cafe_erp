package com.cafe.erp.modules.pos.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class PosConsumptionDTO {
    private Long materialId;
    private String materialName;
    private BigDecimal usedQty;
    private String uomCode;
}

