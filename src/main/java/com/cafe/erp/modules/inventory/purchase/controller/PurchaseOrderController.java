package com.cafe.erp.modules.inventory.purchase.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderCreateRequest;
import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderDTO;
import com.cafe.erp.modules.inventory.purchase.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> create(@RequestBody PurchaseOrderCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success( "Purchase Order Created", service.createPurchaseOrder(req),201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrderDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Purchase Orders",service.getAll(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Purchase Order",service.getById(id), 200));
    }

    @PatchMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> submit(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Submitted",service.submit(id), 200));
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<PurchaseOrderDTO>> approve(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Approved",service.approve(id), 200));
    }
}

