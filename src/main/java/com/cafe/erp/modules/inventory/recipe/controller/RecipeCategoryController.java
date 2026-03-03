package com.cafe.erp.modules.inventory.recipe.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryRequest;
import com.cafe.erp.modules.inventory.recipe.dto.RecipeCategoryResponse;
import com.cafe.erp.modules.inventory.recipe.service.RecipeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/recipes/categories")
@RequiredArgsConstructor
public class RecipeCategoryController {

    private final RecipeCategoryService service;

    @PostMapping
    public ResponseEntity<ApiResponse<RecipeCategoryResponse>> create(@RequestBody RecipeCategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Category created", service.createCategory(request), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecipeCategoryResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Categories fetched", service.getAllCategories(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeCategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Category fetched", service.getCategory(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeCategoryResponse>> update(@PathVariable Long id, @RequestBody RecipeCategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Category updated", service.updateCategory(id, request), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteCategory(id);
      return ResponseEntity.ok(ApiResponse.success("Category deleted", null, 200));
    }
}

