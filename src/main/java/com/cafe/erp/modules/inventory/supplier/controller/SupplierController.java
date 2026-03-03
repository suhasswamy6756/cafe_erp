package com.cafe.erp.modules.inventory.supplier.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDTO>> create(@RequestBody SupplierCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("created successfully", supplierService.createSupplier(request), 201));
    }

    @PutMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<SupplierDTO>> update(
            @PathVariable Long supplierId,
            @RequestBody SupplierUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success("Updated Successfully ", supplierService.updateSupplier(supplierId, request), 200));
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<SupplierDTO>> get(@PathVariable Long supplierId) {
        return ResponseEntity.ok(ApiResponse.success("Retrieved Successfully ", supplierService.getSupplier(supplierId), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Retrieved Successfully ", supplierService.getAllSuppliers(), 200));
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(ApiResponse.success("Deleted Successfully ", null, 200));
    }
}
