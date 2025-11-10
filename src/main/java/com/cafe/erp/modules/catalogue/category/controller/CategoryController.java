package com.cafe.erp.modules.catalogue.category.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.category.dto.CategoryRequestDTO;
import com.cafe.erp.modules.catalogue.category.dto.CategoryResponseDTO;
import com.cafe.erp.modules.catalogue.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {



    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", categoryResponseDTO, 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categoryService.getAllCategories(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Category fetched successfully", categoryService.getCategoryById(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO categoryRequestDTO) {
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", categoryService.updateCategory(id, categoryRequestDTO), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null, 200));
    }
}

