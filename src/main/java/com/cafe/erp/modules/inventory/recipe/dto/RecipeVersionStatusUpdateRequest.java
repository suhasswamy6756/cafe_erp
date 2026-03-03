package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

@Data
public class RecipeVersionStatusUpdateRequest {
    private String status;      // ACTIVE / TESTING / ARCHIVED
    private Boolean makeDefault;
}

