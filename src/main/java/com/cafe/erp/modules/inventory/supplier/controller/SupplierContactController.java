package com.cafe.erp.modules.inventory.supplier.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.service.SupplierContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory/suppliers/contacts")
@RequiredArgsConstructor
public class SupplierContactController {

    private final SupplierContactService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierContactDTO>> create(@RequestBody SupplierContactCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Supplier contact created successfully", service.create(req), 201));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ApiResponse<SupplierContactDTO>> update(
            @PathVariable Long contactId,
            @RequestBody SupplierContactUpdateRequest req
    ) {
        return ResponseEntity.ok(ApiResponse.success("Supplier contact Updates succesfully", service.update(contactId, req), 200));
    }

    @GetMapping("/{contactId}")
    public ResponseEntity<ApiResponse<SupplierContactDTO>> get(@PathVariable Long contactId) {
     return ResponseEntity.ok(ApiResponse.success("Supplier contact fetched successfully", service.get(contactId), 200));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<SupplierContactDTO>>> listBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(ApiResponse.success("Supplier contacts fetched successfully", service.listBySupplier(supplierId), 200));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long contactId) {
        service.softDelete(contactId);
        return ResponseEntity.ok(ApiResponse.success("Supplier contact deleted successfully", null, 200));
    }
}

