package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {
    private Long recipeId;
    private String recipeName;
    private Long categoryId;
    private String categoryName;
    private String outputUnit;
    private BigDecimal outputQuantity;

    // Current/default version
    private RecipeVersionDTO defaultVersion;

    // All versions (optional for detail API)
    private List<RecipeVersionDTO> versions;
}


