package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

@Data
public class RecipeVersionCloneRequest {
    private Long sourceVersionId;
    private String notes;
}

