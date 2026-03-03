package com.cafe.erp.modules.inventory.supplier.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.service.SupplierAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/suppliers/addresses")
@RequiredArgsConstructor
public class SupplierAddressController {

    private final SupplierAddressService service;

    // ---------------------------------------------------------
    // CREATE ADDRESS
    // ---------------------------------------------------------
    @PostMapping
    public ResponseEntity<ApiResponse<SupplierAddressDTO>> create(@RequestBody SupplierAddressCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Supplier address created successfully",service.create(req), 201));
    }

    // ---------------------------------------------------------
    // UPDATE ADDRESS
    // ---------------------------------------------------------
    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<SupplierAddressDTO>> update(
            @PathVariable Long addressId,
            @RequestBody SupplierAddressUpdateRequest req
    ) {
        return ResponseEntity.ok(ApiResponse.success("Supplier address updated successfully",service.update(addressId, req), 200));

    }

    // ---------------------------------------------------------
    // GET SINGLE ADDRESS
    // ---------------------------------------------------------
    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<SupplierAddressDTO>> get(@PathVariable Long addressId) {
        return ResponseEntity.ok(ApiResponse.success("Supplier address retrieved successfully",service.get(addressId), 200));
    }

    // ---------------------------------------------------------
    // LIST ALL ADDRESSES FOR SUPPLIER
    // ---------------------------------------------------------
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<SupplierAddressDTO>>> listBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(ApiResponse.success("Supplier addresses retrieved successfully",service.listBySupplier(supplierId), 200));
    }

    // ---------------------------------------------------------
    // SOFT DELETE ADDRESS
    // ---------------------------------------------------------
    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long addressId) {
        service.softDelete(addressId);
        return ResponseEntity.ok(ApiResponse.success("Supplier address deleted successfully", null, 200));

    }
}

