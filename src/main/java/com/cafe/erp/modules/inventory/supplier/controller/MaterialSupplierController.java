package com.cafe.erp.modules.inventory.supplier.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierRequest;
import com.cafe.erp.modules.inventory.supplier.service.ItemSupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/material-suppliers")
@RequiredArgsConstructor
public class MaterialSupplierController {

    private final ItemSupplierService service;

    @PostMapping
    public ResponseEntity<ApiResponse<MaterialSupplierDTO>> assign(@RequestBody MaterialSupplierRequest req) {
       return ResponseEntity.ok(ApiResponse.success("Item supplier assigned successfully",service.assign(req), 201));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<List<MaterialSupplierDTO>>> listByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(ApiResponse.success("fetched successfully", service.listByItem(itemId), 200));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<MaterialSupplierDTO>>> listBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(ApiResponse.success("fetched successfully", service.listBySupplier(supplierId), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("deleted successfully", null, 200));
    }
}
