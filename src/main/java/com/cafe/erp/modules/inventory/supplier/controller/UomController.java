package com.cafe.erp.modules.inventory.supplier.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.UomCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.UomDTO;
import com.cafe.erp.modules.inventory.supplier.dto.UomUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.service.UomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/uoms")
@RequiredArgsConstructor
public class UomController {

    private final UomService uomService;

    // -------------------------
    // CREATE
    // -------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<UomDTO>> create(@RequestBody UomCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success(" UOM created successfully ",uomService.create(req), 201));
    }

    // -------------------------
    // UPDATE
    // -------------------------
    @PutMapping("/{code}")
    public ResponseEntity<ApiResponse<UomDTO>> update(
            @PathVariable String code,
            @RequestBody UomUpdateRequest req
    ) {
       return ResponseEntity.ok(ApiResponse.success(" UOM updated successfully ",uomService.update(code, req), 200));
    }

    // -------------------------
    // GET SINGLE
    // -------------------------
    @GetMapping("/{code}")
    public ResponseEntity<ApiResponse<UomDTO>> get(@PathVariable String code) {
        return ResponseEntity.ok(ApiResponse.success(" UOM fetched successfully ",uomService.get(code), 200));
    }

    // -------------------------
    // GET ALL ACTIVE UOMs
    // -------------------------
    @GetMapping
    public ResponseEntity<ApiResponse<List<UomDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(" UOMs fetched successfully ",uomService.getAll(), 200));
    }

    // -------------------------
    // SOFT DELETE
    // -------------------------
    @DeleteMapping("/{code}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String code) {
        uomService.delete(code);
       return ResponseEntity.ok(ApiResponse.success(" UOM deleted successfully ",null, 200));
    }
}

