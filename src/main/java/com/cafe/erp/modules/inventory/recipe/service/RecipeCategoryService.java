package com.cafe.erp.modules.inventory.recipe.service;

import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryRequest;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryResponse;

import java.util.List;

public interface RecipeCategoryService {
    RecipeCategoryResponse createCategory(RecipeCategoryRequest req);

    List<RecipeCategoryResponse> getAllCategories();

    RecipeCategoryResponse getCategory(Long id);

    RecipeCategoryResponse updateCategory(Long id, RecipeCategoryRequest req);

    void deleteCategory(Long id);
}

