package com.cafe.erp.modules.inventory.recipe.mapper;

import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryResponse;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeItemDTO;

import com.cafe.erp.modules.inventory.recipe.dto.RecipeResponseDTO;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeVersionDTO;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeCategory;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeItems;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeVersions;
import com.cafe.erp.modules.inventory.recipe.entity.Recipes;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class RecipeMapper {

    public RecipeCategoryResponse toCategoryDTO(RecipeCategory c) {
        RecipeCategoryResponse dto = new RecipeCategoryResponse();
        dto.setRecipeCategoryId(c.getRecipeCategoryId());
        dto.setRecipeCategoryName(c.getRecipeCategoryName());
        dto.setRecipeCategoryName(c.getRecipeCategoryName());
        return dto;
    }

    public RecipeItemDTO toItemDTO(RecipeItems entity) {
        RecipeItemDTO dto = new RecipeItemDTO() ;
        dto.setMaterialId(entity.getMaterial().getMaterialId());
        dto.setMaterialName(entity.getMaterial().getName());
        dto.setQuantity(entity.getQuantity());
        dto.setUomCode(entity.getUomCode());
        dto.setCostPerUnit(entity.getCostPerUnit());
        dto.setTotalCost(entity.getTotalCost());
        return dto;
    }

    public RecipeVersionDTO toVersionDTO(RecipeVersions version,
                                         BigDecimal totalCost,
                                         BigDecimal costPerOutputUnit,
                                         List<RecipeItems> items) {

        RecipeVersionDTO dto = new RecipeVersionDTO();
        dto.setVersionId(version.getVersionId());
        dto.setVersionNumber(version.getVersionNumber());
        dto.setStatus(version.getStatus());
        dto.setIsDefault(version.getIsDefault());
        dto.setNotes(version.getNote());
        dto.setTotalCost(totalCost);
        dto.setCostPerOutputUnit(costPerOutputUnit);

        dto.setItems(
                items.stream()
                        .map(this::toItemDTO)
                        .toList()
        );

        return dto;
    }

    public RecipeResponseDTO toRecipeDTO(Recipes recipe,
                                         RecipeVersionDTO defaultVersion,
                                         List<RecipeVersionDTO> versions) {

        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setRecipeId(recipe.getRecipeId());
        dto.setRecipeName(recipe.getRecipeName());
        dto.setCategoryId(
                recipe.getRecipeCategory() != null ? recipe.getRecipeCategory().getRecipeCategoryId() : null
        );
        dto.setCategoryName(
                recipe.getRecipeCategory() != null ? recipe.getRecipeCategory().getRecipeCategoryName() : null
        );
        dto.setOutputUnit(recipe.getOutputUnit());
        dto.setOutputQuantity(recipe.getOutputQuantity());

        dto.setDefaultVersion(defaultVersion);
        dto.setVersions(versions);

        return dto;
    }
}


