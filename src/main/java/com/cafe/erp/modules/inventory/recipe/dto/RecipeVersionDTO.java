package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RecipeVersionDTO {
    private Long versionId;
    private Long versionNumber;
    private String status;             // ACTIVE / TESTING / ARCHIVED
    private Boolean isDefault;
    private String notes;
    private BigDecimal totalCost;
    private BigDecimal costPerOutputUnit;
    private List<RecipeItemDTO> items;
}

