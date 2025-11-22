package com.cafe.erp.modules.inventory.stock.controller;



import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.inventory.stock.dto.*;
import com.cafe.erp.modules.inventory.stock.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // ---------- STOCK ----------

    @PostMapping("/stocks")
    public ResponseEntity<ApiResponse<StockDTO>> createStock(@RequestBody StockCreateRequest req) {
        StockDTO dto = inventoryService.createStock(req);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Stock created successfully", dto, 201));
    }

    @GetMapping("/stocks/item/{itemId}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStockByItem(@PathVariable Long itemId) {
        List<StockDTO> data = inventoryService.getStockByItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Stocks fetched", data, 200));
    }

    @GetMapping("/stocks/location/{locationId}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStockByLocation(@PathVariable Long locationId) {
        List<StockDTO> data = inventoryService.getStockByLocation(locationId);
        return ResponseEntity.ok(ApiResponse.success("Stocks fetched", data, 200));
    }

    @GetMapping("/stocks/item/{itemId}/location/{locationId}")
    public ResponseEntity<ApiResponse<List<StockDTO>>> getStockByItemAndLocation(
            @PathVariable Long itemId,
            @PathVariable Long locationId
    ) {
        List<StockDTO> data = inventoryService.getStockByItemAndLocation(itemId, locationId);
        return ResponseEntity.ok(ApiResponse.success("Stocks fetched", data, 200));
    }

    @DeleteMapping("/stocks/{stockId}")
    public ResponseEntity<ApiResponse<Void>> softDeleteStock(@PathVariable Long stockId) {
        inventoryService.softDeleteStock(stockId);
        return ResponseEntity.ok(ApiResponse.success("Stock deleted (soft)", null, 200));
    }

    // ---------- STOCK COUNT ----------

    @PostMapping("/stock-counts")
    public ResponseEntity<ApiResponse<StockCountDTO>> openStockCount(@RequestBody StockCountCreateRequest req) {
        StockCountDTO dto = inventoryService.openStockCount(req);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Stock count opened", dto, 201));
    }

    @GetMapping("/stock-counts/{countId}")
    public ResponseEntity<ApiResponse<StockCountDTO>> getStockCount(@PathVariable Long countId) {
        StockCountDTO dto = inventoryService.getStockCount(countId);
        return ResponseEntity.ok(ApiResponse.success("Stock count fetched", dto, 200));
    }

    @GetMapping("/stock-counts/location/{locationId}")
    public ResponseEntity<ApiResponse<List<StockCountDTO>>> getCountsByLocation(@PathVariable Long locationId) {
        List<StockCountDTO> data = inventoryService.getStockCountsByLocation(locationId);
        return ResponseEntity.ok(ApiResponse.success("Stock counts fetched", data, 200));
    }

    @GetMapping("/stock-counts")
    public ResponseEntity<ApiResponse<List<StockCountDTO>>> getCountsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    ) {
        List<StockCountDTO> data = inventoryService.getStockCountsByDateRange(start, end);
        return ResponseEntity.ok(ApiResponse.success("Stock counts fetched", data, 200));
    }

    @PostMapping("/stock-count-items")
    public ResponseEntity<ApiResponse<StockCountItemDTO>> addStockCountItem(
            @RequestBody StockCountItemCreateRequest req
    ) {
        StockCountItemDTO dto = inventoryService.addStockCountItem(req);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Stock count item added", dto, 201));
    }

    @GetMapping("/stock-count-items/{countId}")
    public ResponseEntity<ApiResponse<List<StockCountItemDTO>>> getStockCountItems(@PathVariable Long countId) {
        List<StockCountItemDTO> data = inventoryService.getStockCountItems(countId);
        return ResponseEntity.ok(ApiResponse.success("Stock count items fetched", data, 200));
    }

    // ---------- ADJUSTMENTS ----------

    @PostMapping("/adjustments")
    public ResponseEntity<ApiResponse<StockAdjustmentDTO>> createAdjustment(
            @RequestBody StockAdjustmentCreateRequest req
    ) {
        StockAdjustmentDTO dto = inventoryService.createAdjustment(req);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Stock adjustment created", dto, 201));
    }

    @GetMapping("/adjustments/item/{itemId}")
    public ResponseEntity<ApiResponse<List<StockAdjustmentDTO>>> getAdjustmentsForItem(
            @PathVariable Long itemId
    ) {
        List<StockAdjustmentDTO> data = inventoryService.getAdjustmentsForItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Adjustments fetched", data, 200));
    }

    @GetMapping("/adjustments")
    public ResponseEntity<ApiResponse<List<StockAdjustmentDTO>>> getAdjustmentsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<StockAdjustmentDTO> data = inventoryService.getAdjustmentsBetween(from, to);
        return ResponseEntity.ok(ApiResponse.success("Adjustments fetched", data, 200));
    }

    // ---------- VALUATIONS ----------

    @GetMapping("/valuations/latest")
    public ResponseEntity<ApiResponse<StockValuationDTO>> getLatestValuation(
            @RequestParam Long itemId,
            @RequestParam Long locationId
    ) {
        StockValuationDTO dto = inventoryService.getLatestValuation(itemId, locationId);
        return ResponseEntity.ok(ApiResponse.success("Latest valuation fetched", dto, 200));
    }

    @GetMapping("/valuations/item/{itemId}")
    public ResponseEntity<ApiResponse<List<StockValuationDTO>>> getValuationsForItem(
            @PathVariable Long itemId
    ) {
        List<StockValuationDTO> data = inventoryService.getValuationsForItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Valuations fetched", data, 200));
    }

    @GetMapping("/valuations/location/{locationId}")
    public ResponseEntity<ApiResponse<List<StockValuationDTO>>> getValuationsForLocation(
            @PathVariable Long locationId
    ) {
        List<StockValuationDTO> data = inventoryService.getValuationsForLocation(locationId);
        return ResponseEntity.ok(ApiResponse.success("Valuations fetched", data, 200));
    }

    // ---------- CHANGE LOGS ----------

    @GetMapping("/logs/item/{itemId}")
    public ResponseEntity<ApiResponse<List<StockChangeLogDTO>>> getLogsForItem(@PathVariable Long itemId) {
        List<StockChangeLogDTO> data = inventoryService.getChangeLogsForItem(itemId);
        return ResponseEntity.ok(ApiResponse.success("Change logs fetched", data, 200));
    }

    @GetMapping("/logs/location/{locationId}")
    public ResponseEntity<ApiResponse<List<StockChangeLogDTO>>> getLogsForLocation(@PathVariable Long locationId) {
        List<StockChangeLogDTO> data = inventoryService.getChangeLogsForLocation(locationId);
        return ResponseEntity.ok(ApiResponse.success("Change logs fetched", data, 200));
    }

    @GetMapping("/logs")
    public ResponseEntity<ApiResponse<List<StockChangeLogDTO>>> getLogsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<StockChangeLogDTO> data = inventoryService.getChangeLogsBetween(from, to);
        return ResponseEntity.ok(ApiResponse.success("Change logs fetched", data, 200));
    }
}

