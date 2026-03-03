package com.cafe.erp.modules.inventory.categories.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryCreateRequest;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryDTO;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryUpdateRequest;
import com.cafe.erp.modules.inventory.categories.service.InventoryCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/categories")
@RequiredArgsConstructor
public class InventoryCategoryController {

    private final InventoryCategoryService service;

    @PostMapping
    public ResponseEntity<ApiResponse<InventoryCategoryDTO>> create(@RequestBody InventoryCategoryCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Category created", service.create(req), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryCategoryDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("fetched categories", service.getAll(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryCategoryDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("fecth category", service.get(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InventoryCategoryDTO>> update(@PathVariable Long id, @RequestBody InventoryCategoryUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Updated Category", service.update(id, req), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted Succesfully", null, 200));
    }
}

