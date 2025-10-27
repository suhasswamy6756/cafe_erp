package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.model.Category;
import com.cafe.erp.catalogue.service.CategoryService;
import com.cafe.erp.common.model.ApiResponse;
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
    public ResponseEntity<ApiResponse<Category>> create(@RequestBody Category category) {
//        return ResponseEntity.ok(200, "Category created successfully", categoryService.createCategory(category));
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", categoryService.createCategory(category), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success( "Cateogry List" , categoryService.getAllCategories(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Category fetched successfully", categoryService.getCategoryById(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", categoryService.updateCategory(id, category), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, @RequestParam String deletedBy) {
        categoryService.softDeleteCategory(id, deletedBy);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null, 200));
    }
}
