package com.cafe.erp.modules.inventory.stock.service;

import com.cafe.erp.modules.inventory.stock.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryService {

    // ---------- STOCK ----------
    StockDTO createStock(StockCreateRequest req);

    List<StockDTO> getStockByItem(Long itemId);

    List<StockDTO> getStockByLocation(Long locationId);

    List<StockDTO> getStockByItemAndLocation(Long itemId, Long locationId);

    void softDeleteStock(Long stockId);


    // ---------- STOCK COUNT (HEADER + LINES) ----------
    StockCountDTO openStockCount(StockCountCreateRequest req);

    StockCountDTO getStockCount(Long countId);

    List<StockCountDTO> getStockCountsByLocation(Long locationId);

    List<StockCountDTO> getStockCountsByDateRange(LocalDate start, LocalDate end);

    StockCountItemDTO addStockCountItem(StockCountItemCreateRequest req);

    List<StockCountItemDTO> getStockCountItems(Long countId);


    // ---------- ADJUSTMENTS ----------
    StockAdjustmentDTO createAdjustment(StockAdjustmentCreateRequest req);

    List<StockAdjustmentDTO> getAdjustmentsForItem(Long itemId);

    List<StockAdjustmentDTO> getAdjustmentsBetween(LocalDateTime from, LocalDateTime to);


    // ---------- VALUATIONS ----------
    StockValuationDTO getLatestValuation(Long itemId, Long locationId);

    List<StockValuationDTO> getValuationsForItem(Long itemId);

    List<StockValuationDTO> getValuationsForLocation(Long locationId);


    // ---------- CHANGE LOGS ----------
    List<StockChangeLogDTO> getChangeLogsForItem(Long itemId);

    List<StockChangeLogDTO> getChangeLogsForLocation(Long locationId);

    List<StockChangeLogDTO> getChangeLogsBetween(LocalDateTime from, LocalDateTime to);
}
