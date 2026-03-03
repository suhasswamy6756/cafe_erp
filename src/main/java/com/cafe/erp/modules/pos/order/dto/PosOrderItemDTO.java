package com.cafe.erp.modules.pos.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PosOrderItemDTO {
    private Long orderItemId;
    private Long recipeId;
    private String recipeName;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private List<PosConsumptionDTO> consumption;
}
