package com.cafe.erp.modules.inventory.stock_adjusments.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentCreateRequest;
import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentResponseDTO;
import com.cafe.erp.modules.inventory.stock_adjusments.service.StockAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/stock-adjustments")
@RequiredArgsConstructor
public class StockAdjustmentController {

    private final StockAdjustmentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<StockAdjustmentResponseDTO>> create(@RequestBody StockAdjustmentCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Stock adjusted successfully", service.createAdjustment(req), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockAdjustmentResponseDTO>> get(@PathVariable Long id) {

        return ResponseEntity.ok(ApiResponse.success("Fetched", service.getById(id), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockAdjustmentResponseDTO>>> list() {
        return ResponseEntity.ok(ApiResponse.success("Fetched", service.getAll(), 200));
    }
}

