package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RecipeCreateRequest {
    private String recipeName;
    private Long categoryId;
    private String outputUnit;

    private BigDecimal outputQuantity;
    private List<RecipeItemCreateRequest> items;
}

