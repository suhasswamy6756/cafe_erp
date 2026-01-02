package com.cafe.erp.modules.inventory.supplier.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.service.SupplierTaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/taxes")
@RequiredArgsConstructor
public class SupplierTaxController {

    private final SupplierTaxService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierTaxDTO>> create(@RequestBody SupplierTaxCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("created successfully", service.create(req), 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierTaxDTO>> update(@PathVariable Long id,
                                                              @RequestBody SupplierTaxUpdateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Updated Successfully ", service.update(id, req), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierTaxDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Retrieved Successfully ", service.get(id), 200));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<SupplierTaxDTO>>> list(@PathVariable Long supplierId) {
        return ResponseEntity.ok(ApiResponse.success("Retrieved Successfully ", service.listBySupplier(supplierId), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted Successfully ", null, 200));
    }
}

