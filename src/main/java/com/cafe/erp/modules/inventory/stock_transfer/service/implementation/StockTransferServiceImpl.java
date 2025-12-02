package com.cafe.erp.modules.inventory.stock_transfer.service.implementation;

import com.cafe.erp.common.enums.StockTransferStatus;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import com.cafe.erp.modules.inventory.stock.repository.StockRepository;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferCreateRequest;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferResponseDTO;
import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferHeader;
import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferItem;
import com.cafe.erp.modules.inventory.stock_transfer.mapper.StockTransferMapper;
import com.cafe.erp.modules.inventory.stock_transfer.repository.StockTransferItemRepository;
import com.cafe.erp.modules.inventory.stock_transfer.repository.StockTransferRepository;
import com.cafe.erp.modules.inventory.stock_transfer.service.StockTransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@AllArgsConstructor
public class StockTransferServiceImpl implements StockTransferService {

    private final StockTransferRepository stockTransferRepository;
    private final StockTransferItemRepository stockTransferItemRepository;
    private final LocationsRepository locationRepository;
    private final MaterialRepository materialRepository;
    private final StockRepository stockRepository;
    private final StockTransferMapper mapper;

    // ------------------ CREATE ------------------
    @Override
    public StockTransferResponseDTO createStockTransfer(StockTransferCreateRequest request) {

        if (request.getFromLocationId().equals(request.getToLocationId()))
            throw new IllegalArgumentException("From and To locations cannot be the same!");

        var fromLocation = locationRepository.findById(request.getFromLocationId())
                .orElseThrow(() -> new RuntimeException("From Location not found"));

        var toLocation = locationRepository.findById(request.getToLocationId())
                .orElseThrow(() -> new RuntimeException("To Location not found"));

        String transferNumber = "ST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        StockTransferHeader header = StockTransferHeader.builder()
                .fromLocation(fromLocation)
                .toLocation(toLocation)
                .remarks(request.getRemarks())
                .transferNumber(transferNumber)
                .status(StockTransferStatus.REQUESTED)
                .build();

        var items = request.getItems().stream().map(reqItem -> {
            Material material = materialRepository.findById(reqItem.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            return StockTransferItem.builder()
                    .transfer(header)
                    .material(material)
                    .uomCode(reqItem.getUomCode())
                    .requestedQty(reqItem.getRequestedQty())
                    .issuedQty(BigDecimal.ZERO)
                    .receivedQty(BigDecimal.ZERO)
                    .shippedQty(BigDecimal.ZERO)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy(getCurrentUser())
                    .updatedBy(getCurrentUser())
//                    .unitCost(reqItem.getUnitCost())
                    .build();
        }).toList();

        header.setItems(items);

        return mapper.toDTO(stockTransferRepository.save(header));
    }

    // ------------------ APPROVE ------------------
    @Override
    public StockTransferResponseDTO approveTransfer(Long id) {

        StockTransferHeader transfer = stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if (transfer.getStatus() != StockTransferStatus.REQUESTED)
            throw new IllegalStateException("Only REQUESTED transfers can be approved.");

        transfer.setStatus(StockTransferStatus.APPROVED);

        return mapper.toDTO(stockTransferRepository.save(transfer));
    }

    // ------------------ ISSUE (DEDUCT STOCK) ------------------
    @Override
    public StockTransferResponseDTO issueTransfer(Long id) {

        StockTransferHeader transfer = stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if (transfer.getStatus() != StockTransferStatus.APPROVED)
            throw new IllegalStateException("Only APPROVED transfers can be issued.");

        transfer.getItems().forEach(item -> {

            Stock sourceStock = stockRepository.findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
                    item.getMaterial().getMaterialId(),
                    transfer.getFromLocation().getLocationId()
            ).orElseThrow(() -> new RuntimeException("Stock not available to issue."));

            if (sourceStock.getQuantity().compareTo(item.getRequestedQty()) < 0)
                throw new IllegalStateException("Insufficient stock to issue this transfer.");

            sourceStock.setQuantity(sourceStock.getQuantity().subtract(item.getRequestedQty()));
            stockRepository.save(sourceStock);

            item.setIssuedQty(item.getRequestedQty());
        });

        transfer.setStatus(StockTransferStatus.ISSUED);
        return mapper.toDTO(stockTransferRepository.save(transfer));
    }

    // ------------------ RECEIVE (ADD STOCK) ------------------
    @Override
    public StockTransferResponseDTO receiveTransfer(Long id) {

        StockTransferHeader transfer = stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if (transfer.getStatus() != StockTransferStatus.ISSUED)
            throw new IllegalStateException("Only ISSUED transfers can be received.");

        transfer.getItems().forEach(item -> {

            Stock destStock = stockRepository.findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
                    item.getMaterial().getMaterialId(),
                    transfer.getToLocation().getLocationId()
            ).orElse(null);

            if (destStock == null) {
                destStock = Stock.builder()
                        .material(item.getMaterial())
                        .location(transfer.getToLocation())
                        .uomCode(item.getUomCode())
                        .quantity(item.getIssuedQty())
                        .build();
            } else {
                destStock.setQuantity(destStock.getQuantity().add(item.getIssuedQty()));
            }

            stockRepository.save(destStock);

            item.setReceivedQty(item.getIssuedQty());
        });

        transfer.setStatus(StockTransferStatus.RECEIVED);
        return mapper.toDTO(stockTransferRepository.save(transfer));
    }

    // ------------------ CANCEL ------------------
    @Override
    public StockTransferResponseDTO cancelTransfer(Long id) {

        StockTransferHeader transfer = stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if (transfer.getStatus() == StockTransferStatus.ISSUED)
            throw new IllegalStateException("Cannot cancel a transfer after issue.");

        transfer.setStatus(StockTransferStatus.CANCELLED);

        return mapper.toDTO(stockTransferRepository.save(transfer));
    }

    // ------------------ CLOSE ------------------
    @Override
    public StockTransferResponseDTO closeTransfer(Long id) {

        StockTransferHeader transfer = stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found"));

        if (transfer.getStatus() != StockTransferStatus.RECEIVED)
            throw new IllegalStateException("Only RECEIVED transfers can be closed.");

        transfer.setStatus(StockTransferStatus.CLOSED);

        return mapper.toDTO(stockTransferRepository.save(transfer));
    }

    // ------------------ GET ------------------
    @Override
    public StockTransferResponseDTO getStockTransfer(Long id) {
        return mapper.toDTO(stockTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transfer not found")));
    }

    @Override
    public List<StockTransferResponseDTO> getAllStockTransfers() {
        return stockTransferRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
