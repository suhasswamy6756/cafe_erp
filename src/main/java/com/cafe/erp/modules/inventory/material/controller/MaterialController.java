package com.cafe.erp.modules.inventory.material.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.material.dto.MaterialCreateRequest;
import com.cafe.erp.modules.inventory.material.dto.MaterialDTO;
import com.cafe.erp.modules.inventory.material.dto.MaterialUpdateRequest;
import com.cafe.erp.modules.inventory.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory/materials")
public class MaterialController {
    private final MaterialService service;

    @PostMapping
    public ResponseEntity<ApiResponse<MaterialDTO>> create(@RequestBody MaterialCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Material created", service.create(req), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MaterialDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("fetched materials", service.getAll(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("fetched material", service.get(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MaterialDTO>> update(@PathVariable Long id, @RequestBody MaterialUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Updated Material", service.update(id, req), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted Succesfully", null, 200));
    }
}
