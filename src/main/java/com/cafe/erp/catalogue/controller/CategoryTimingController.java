package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.model.CategoryTiming;
import com.cafe.erp.catalogue.service.CategoryTimingService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category-timings")
public class CategoryTimingController {

    private final CategoryTimingService categoryTimingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryTiming>>> getAllCategoryTimings() {
        return ResponseEntity.ok(ApiResponse.success("CategoryTimings fetched successfully", categoryTimingService.getAllCategoryTimings(), 200));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<CategoryTiming>>> getCategoryTimingById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("CategoryTiming fetched successfully", categoryTimingService.getCategoryTimingById(id), 200));
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryTiming>> createCategoryTiming(@RequestBody CategoryTiming categoryTiming) {
        return ResponseEntity.ok(ApiResponse.success("CategoryTiming created successfully", categoryTimingService.createCategoryTiming(categoryTiming), 201));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryTiming>> updateCategoryTiming(@PathVariable Long id, @RequestBody CategoryTiming categoryTiming) {
        return ResponseEntity.ok(ApiResponse.success("CategoryTiming updated successfully", categoryTimingService.updateCategoryTiming(id, categoryTiming), 200));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryTiming(@PathVariable Long id) {
        categoryTimingService.deleteCategoryTiming(id);
        return ResponseEntity.ok(ApiResponse.success("CategoryTiming deleted successfully", null, 200));
    }


}
