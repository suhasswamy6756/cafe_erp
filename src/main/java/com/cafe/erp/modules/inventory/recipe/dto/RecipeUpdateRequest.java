package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeUpdateRequest {
    private String recipeName;
    private Long categoryId;
    private String outputUnit;
    private BigDecimal outputQuantity;
}

