package com.cafe.erp.modules.inventory.stock.service.implementation;

import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.repository.ItemRepository;
import com.cafe.erp.modules.inventory.stock.dto.*;
import com.cafe.erp.modules.inventory.stock.entity.*;
import com.cafe.erp.modules.inventory.stock.mapper.*;
import com.cafe.erp.modules.inventory.stock.repository.*;
import com.cafe.erp.modules.inventory.stock.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final StockRepository stockRepository;
    private final StockCountRepository stockCountRepository;
    private final StockCountItemRepository stockCountItemRepository;
    private final StockAdjustmentRepository stockAdjustmentRepository;
    private final StockChangeLogRepository stockChangeLogRepository;
    private final StockValuationRepository stockValuationRepository;

    private final ItemRepository itemRepository;
    private final LocationsRepository locationRepository;

    private final StockMapper stockMapper;
    private final StockCountMapper stockCountMapper;
    private final StockCountItemMapper stockCountItemMapper;
    private final StockAdjustmentMapper stockAdjustmentMapper;
    private final StockChangeLogMapper stockChangeLogMapper;
    private final StockValuationMapper stockValuationMapper;

    // =========================================================
    //                    STOCK
    // =========================================================

    @Override
    public StockDTO createStock(StockCreateRequest req) {
        Item item = itemRepository.findById(req.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found: " + req.getItemId()));

        Location location = locationRepository.findById(req.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found: " + req.getLocationId()));

        // Current total qty at this location for this item
        BigDecimal oldQty = getOnHandQuantity(item.getId(), location.getLocationId());

        Stock stock = stockMapper.toEntity(req, item, location);
        stock = stockRepository.save(stock);

        // New total qty
        BigDecimal newQty = oldQty.add(stock.getQuantity());

        // Log the change
        logStockChange(item, location,
                "STOCK_CREATED",
                oldQty,
                newQty,
                stock.getUnitCost()
        );

        // Recalculate valuation (simple WAC logic)
        recalculateValuation(item, location, newQty, stock.getUnitCost());

        return stockMapper.toDTO(stock);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getStockByItem(Long itemId) {
        return stockRepository.findByItem_IdAndIsDeletedFalse(itemId)
                .stream()
                .map(stockMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getStockByLocation(Long locationId) {
        return stockRepository.findByLocation_LocationIdAndIsDeletedFalse(locationId)
                .stream()
                .map(stockMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> getStockByItemAndLocation(Long itemId, Long locationId) {
        return stockRepository
                .findByItem_IdAndLocation_LocationIdAndIsDeletedFalse(itemId, locationId)
                .stream()
                .map(stockMapper::toDTO)
                .toList();
    }

    @Override
    public void softDeleteStock(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found: " + stockId));

        stock.setIsDeleted(true);
        stockRepository.save(stock);

        // Optional: you could also log this as a stock change if needed
    }

    // =========================================================
    //                    STOCK COUNTS
    // =========================================================

    @Override
    public StockCountDTO openStockCount(StockCountCreateRequest req) {
        Location location = locationRepository.findById(req.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found: " + req.getLocationId()));

        StockCount count = stockCountMapper.toEntity(req, location);
        count = stockCountRepository.save(count);

        return stockCountMapper.toDTO(count);
    }

    @Override
    @Transactional(readOnly = true)
    public StockCountDTO getStockCount(Long countId) {
        StockCount count = stockCountRepository.findById(countId)
                .orElseThrow(() -> new RuntimeException("Stock count not found: " + countId));
        return stockCountMapper.toDTO(count);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockCountDTO> getStockCountsByLocation(Long locationId) {
        return stockCountRepository.findByLocation_LocationIdAndIsDeletedFalse(locationId)
                .stream()
                .map(stockCountMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockCountDTO> getStockCountsByDateRange(LocalDate start, LocalDate end) {
        return stockCountRepository.findByDateBetweenAndIsDeletedFalse(start, end)
                .stream()
                .map(stockCountMapper::toDTO)
                .toList();
    }

    @Override
    public StockCountItemDTO addStockCountItem(StockCountItemCreateRequest req) {
        StockCount count = stockCountRepository.findById(req.getCountId())
                .orElseThrow(() -> new RuntimeException("Stock count not found: " + req.getCountId()));

        Item item = itemRepository.findById(req.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found: " + req.getItemId()));

        StockCountItem sci = stockCountItemMapper.toEntity(req, count, item);
        sci = stockCountItemRepository.save(sci);

        return stockCountItemMapper.toDTO(sci);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockCountItemDTO> getStockCountItems(Long countId) {
        return stockCountItemRepository.findByCount_CountId(countId)
                .stream()
                .map(stockCountItemMapper::toDTO)
                .toList();
    }

    // =========================================================
    //                    ADJUSTMENTS
    // =========================================================

    @Override
    public StockAdjustmentDTO createAdjustment(StockAdjustmentCreateRequest req) {
        Item item = itemRepository.findById(req.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found: " + req.getItemId()));

        // You may also want a location in adjustment request depending on how you model it.
        // For now, we treat "global item" adjustment – or you can extend the DTO.

        // For logging & valuation we NEED a location, so in real design the DTO should have locationId.
        // Here I’ll just skip valuation update if location is unknown.
        // (You can later extend StockAdjustmentCreateRequest to add locationId.)

        StockAdjustment sa = stockAdjustmentMapper.toEntity(req, item);
        sa = stockAdjustmentRepository.save(sa);

        // If you add locationId to request:
        // Location location = locationRepository.findById(req.getLocationId())...
        // BigDecimal oldQty = getOnHandQuantity(item.getId(), location.getLocationId());
        // BigDecimal newQty = applyEffect(oldQty, req.getEffect(), req.getQuantity());
        // logStockChange(item, location, "ADJUSTMENT", oldQty, newQty, BigDecimal.ZERO);
        // recalculateValuation(item, location, newQty, <some cost logic>);

        return stockAdjustmentMapper.toDTO(sa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockAdjustmentDTO> getAdjustmentsForItem(Long itemId) {
        return stockAdjustmentRepository.findByItem_Id(itemId)
                .stream()
                .map(stockAdjustmentMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockAdjustmentDTO> getAdjustmentsBetween(LocalDateTime from, LocalDateTime to) {
        return stockAdjustmentRepository.findByAdjustedAtBetween(from, to)
                .stream()
                .map(stockAdjustmentMapper::toDTO)
                .toList();
    }

    // =========================================================
    //                    VALUATION
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public StockValuationDTO getLatestValuation(Long itemId, Long locationId) {
        StockValuation v = stockValuationRepository
                .findTopByItem_IdAndLocation_LocationIdOrderByValuationDateDesc(itemId, locationId);
        return stockValuationMapper.toDTO(v);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockValuationDTO> getValuationsForItem(Long itemId) {
        return stockValuationRepository.findByItem_Id(itemId)
                .stream()
                .map(stockValuationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockValuationDTO> getValuationsForLocation(Long locationId) {
        return stockValuationRepository.findByLocation_LocationId(locationId)
                .stream()
                .map(stockValuationMapper::toDTO)
                .toList();
    }

    // =========================================================
    //                    CHANGE LOGS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<StockChangeLogDTO> getChangeLogsForItem(Long itemId) {
        return stockChangeLogRepository.findByItem_Id(itemId)
                .stream()
                .map(stockChangeLogMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockChangeLogDTO> getChangeLogsForLocation(Long locationId) {
        return stockChangeLogRepository.findByLocation_LocationId(locationId)
                .stream()
                .map(stockChangeLogMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockChangeLogDTO> getChangeLogsBetween(LocalDateTime from, LocalDateTime to) {
        return stockChangeLogRepository.findByChangeTimestampBetween(from, to)
                .stream()
                .map(stockChangeLogMapper::toDTO)
                .toList();
    }

    // =========================================================
    //              INTERNAL HELPERS (valuation + logs)
    // =========================================================

    private BigDecimal getOnHandQuantity(Long itemId, Long locationId) {
        return stockRepository
                .findByItem_IdAndLocation_LocationIdAndIsDeletedFalse(itemId, locationId)
                .stream()
                .map(Stock::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void logStockChange(Item item,
                                Location location,
                                String changeType,
                                BigDecimal oldQty,
                                BigDecimal newQty,
                                BigDecimal costPerUom) {

        StockChangeLog log = new StockChangeLog();
        log.setItem(item);
        log.setLocation(location);
        log.setChangeType(changeType);
        log.setOldQuantity(oldQty);
        log.setNewQuantity(newQty);
        log.setQuantityDiff(newQty.subtract(oldQty));
        log.setCostPerUom(costPerUom);
        log.setChangeTimestamp(LocalDateTime.now());

        stockChangeLogRepository.save(log);
    }

    private void recalculateValuation(Item item,
                                      Location location,
                                      BigDecimal newTotalQty,
                                      BigDecimal newUnitCost) {

        if (newTotalQty == null || newTotalQty.compareTo(BigDecimal.ZERO) <= 0) {
            // optional: clear valuation or set to 0
            return;
        }

        StockValuation last = stockValuationRepository
                .findTopByItem_IdAndLocation_LocationIdOrderByValuationDateDesc(
                        item.getId(),
                        location.getLocationId()
                );

        BigDecimal oldQty = BigDecimal.ZERO;
        BigDecimal oldWac = BigDecimal.ZERO;

        if (last != null) {
            oldWac = last.getWeightedAvgCost() != null ? last.getWeightedAvgCost() : BigDecimal.ZERO;
            // oldQty = last.getTotalValue() / oldWac
            if (oldWac.compareTo(BigDecimal.ZERO) > 0 && last.getTotalValue() != null) {
                oldQty = last.getTotalValue().divide(oldWac, 6, BigDecimal.ROUND_HALF_UP);
            }
        }

        // Very simple WAC: (oldVal + newVal) / (oldQty + deltaQty)
        // Here we assume newTotalQty is the latest on-hand,
        // and we approximate "delta" as newTotalQty - oldQty.
        BigDecimal deltaQty = newTotalQty.subtract(oldQty);
        if (deltaQty.compareTo(BigDecimal.ZERO) <= 0) {
            // no positive delta -> skip or handle differently
            return;
        }

        BigDecimal oldValue = oldQty.multiply(oldWac);
        BigDecimal newValue = deltaQty.multiply(newUnitCost);

        BigDecimal totalQty = oldQty.add(deltaQty);
        if (totalQty.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        BigDecimal newWac = oldValue.add(newValue)
                .divide(totalQty, 6, BigDecimal.ROUND_HALF_UP);

        StockValuation v = new StockValuation();
        v.setItem(item);
        v.setLocation(location);
        v.setWeightedAvgCost(newWac);
        v.setTotalValue(newTotalQty.multiply(newWac));
        v.setValuationDate(LocalDateTime.now());

        stockValuationRepository.save(v);
    }
}
