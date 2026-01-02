package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeVersionCreateRequest {
    private String notes;
    private List<RecipeItemCreateRequest> items;
}

