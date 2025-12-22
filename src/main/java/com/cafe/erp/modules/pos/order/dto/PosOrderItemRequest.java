package com.cafe.erp.modules.pos.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PosOrderItemRequest {
    private Long recipeId;
    private BigDecimal quantity;
}

