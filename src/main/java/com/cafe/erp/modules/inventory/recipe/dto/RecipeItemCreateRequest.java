package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeItemCreateRequest {
    private Long materialId;
    private BigDecimal quantity;
    private String uomCode;


}

