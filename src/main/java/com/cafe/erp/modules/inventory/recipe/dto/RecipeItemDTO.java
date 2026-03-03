package com.cafe.erp.modules.inventory.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeItemDTO {
    private Long materialId;
    private String materialName;
    private BigDecimal quantity;
    private String uomCode;
    private BigDecimal costPerUnit;
    private BigDecimal totalCost;
}

