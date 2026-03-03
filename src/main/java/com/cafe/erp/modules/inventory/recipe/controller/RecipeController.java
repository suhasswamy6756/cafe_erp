package com.cafe.erp.modules.inventory.recipe.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.recipe.dto.*;
import com.cafe.erp.modules.inventory.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService service;

    // ------ Recipe ------
    @PostMapping
    public ResponseEntity<ApiResponse<RecipeResponseDTO>> create(@RequestBody RecipeCreateRequest request) {
       return ResponseEntity.ok(ApiResponse.success("Recipe created", service.createRecipe(request), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RecipeResponseDTO>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Recipe list", service.listRecipes(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeResponseDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Recipe fetched", service.getRecipe(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeResponseDTO>> update(@PathVariable Long id,
                                                 @RequestBody RecipeUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Recipe updated", service.updateRecipe(id, request), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.deleteRecipe(id);
       return ResponseEntity.ok(ApiResponse.success("Recipe deleted", null, 200));
    }

    // ------ Versions ------
    @PostMapping("/{recipeId}/versions")
    public ResponseEntity<ApiResponse<RecipeVersionDTO>> createVersion(@PathVariable Long recipeId,
                                                       @RequestBody RecipeVersionCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Version created", service.createVersion(recipeId, request), 201));
    }

    @PostMapping("/{recipeId}/versions/clone")
    public ResponseEntity<ApiResponse<RecipeVersionDTO>> cloneVersion(@PathVariable Long recipeId,
                                                      @RequestBody RecipeVersionCloneRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Version cloned", service.cloneVersion(recipeId, request), 201));
    }

    @PatchMapping("/versions/{versionId}/status")
    public ResponseEntity<ApiResponse<RecipeVersionDTO>> updateStatus(@PathVariable Long versionId,
                                                      @RequestBody RecipeVersionStatusUpdateRequest request) {
     return ResponseEntity.ok(ApiResponse.success("Version status updated", service.updateVersionStatus(versionId, request), 200));
    }

    @PostMapping("/versions/{versionId}/recalculate-cost")
    public ResponseEntity<ApiResponse<RecipeVersionDTO>> recalc(@PathVariable Long versionId) {
       return ResponseEntity.ok(ApiResponse.success("Version cost recalculated", service.recalculateVersionCost(versionId), 200));
    }
}
