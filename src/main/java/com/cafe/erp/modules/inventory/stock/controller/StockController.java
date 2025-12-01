package com.cafe.erp.modules.inventory.stock.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.stock.dto.StockCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockDTO;
import com.cafe.erp.modules.inventory.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @PostMapping
    public ResponseEntity<ApiResponse<StockDTO>> create(@RequestBody StockCreateRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Stock added", service.createStock(req), 201));
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> byMaterial(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock by material", service.getStockByMaterial(id), 200));
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> byLocation(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock by location", service.getStockByLocation(id), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockDTO>>> all() {
        return ResponseEntity.ok(ApiResponse.success("All stocks", service.getAll(), 200));
    }
}

