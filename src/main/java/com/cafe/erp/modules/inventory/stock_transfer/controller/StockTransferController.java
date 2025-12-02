package com.cafe.erp.modules.inventory.stock_transfer.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferCreateRequest;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferResponseDTO;
import com.cafe.erp.modules.inventory.stock_transfer.service.StockTransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory/stock-transfer")
public class StockTransferController {

    private final StockTransferService stockTransferService;

    @PostMapping
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> createStockTransfer(@RequestBody StockTransferCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer created", stockTransferService.createStockTransfer(request), 201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> getStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer retrieved", stockTransferService.getStockTransfer(id), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockTransferResponseDTO>>> getAllStockTransfers() {
        return ResponseEntity.ok(ApiResponse.success("Stock transfers retrieved", stockTransferService.getAllStockTransfers(), 200));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> approveStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer approved", stockTransferService.approveTransfer(id), 200));
    }

    @PutMapping("/{id}/issue")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> issueStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer issued", stockTransferService.issueTransfer(id), 200));
    }

    @PutMapping("/{id}/receive")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> receiveStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer received", stockTransferService.receiveTransfer(id), 200));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> cancelStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer cancelled", stockTransferService.cancelTransfer(id), 200));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<ApiResponse<StockTransferResponseDTO>> closeStockTransfer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Stock transfer closed", stockTransferService.closeTransfer(id), 200));
    }


}
