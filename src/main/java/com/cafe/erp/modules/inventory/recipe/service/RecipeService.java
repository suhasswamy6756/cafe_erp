package com.cafe.erp.modules.inventory.recipe.service;

import com.cafe.erp.modules.inventory.recipe.dto.*;

import java.util.List;

public interface RecipeService {

    RecipeResponseDTO createRecipe(RecipeCreateRequest request);

    RecipeResponseDTO getRecipe(Long recipeId);

    List<RecipeResponseDTO> listRecipes();

    RecipeResponseDTO updateRecipe(Long recipeId, RecipeUpdateRequest request);

    void deleteRecipe(Long recipeId);

    // Versions
    RecipeVersionDTO createVersion(Long recipeId, RecipeVersionCreateRequest request);

    RecipeVersionDTO cloneVersion(Long recipeId, RecipeVersionCloneRequest request);

    RecipeVersionDTO updateVersionStatus(Long versionId, RecipeVersionStatusUpdateRequest request);

    RecipeVersionDTO recalculateVersionCost(Long versionId);

    void recalculateCostsForMaterial(Long materialId); // called from GRN if you want
}


