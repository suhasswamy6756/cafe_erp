package com.cafe.erp.modules.inventory.stock_transfer.service.implementation;

import com.cafe.erp.common.enums.StockTransferStatus;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
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

import java.util.List;

@Service
@AllArgsConstructor
public class StockTransferServiceImpl implements StockTransferService {

    private final StockTransferRepository stockTransferRepository;
    private final StockTransferItemRepository stockTransferItemRepository;
    private final LocationsRepository locationRepository;
    private final MaterialRepository materialRepository;

    private final StockTransferMapper mapper;

    @Override
    public StockTransferResponseDTO createStockTransfer(StockTransferCreateRequest request) {
        if (request.getFromLocationId().equals(request.getToLocationId())) {
            throw new IllegalArgumentException("From location and to location cannot be same");
        }
        StockTransferHeader header = StockTransferHeader.builder()
                .fromLocation(locationRepository.getById(request.getToLocationId()))
                .toLocation(locationRepository.getById(request.getToLocationId()))
                .remarks(request.getRemarks())
                .status(StockTransferStatus.REQUESTED)
                .build();
        var items = request.getItems().stream().map(reqItem -> {
            Material m = materialRepository.findById(reqItem.getMaterialId()).orElseThrow();
            return StockTransferItem.builder()
                    .transfer(header)
                    .material(m)
                    .uomCode(reqItem.getUomCode())
                    .requestedQty(reqItem.getRequestedQty())
                    .build();
        }).toList();

        header.setItems(items);
        return mapper.toDTO(stockTransferRepository.save(header));
    }

    @Override
    public StockTransferResponseDTO getStockTransfer(Long id) {
        return mapper.toDTO(stockTransferRepository.findById(id).orElseThrow());
    }

    @Override
    public List<StockTransferResponseDTO> getAllStockTransfers() {
       return stockTransferRepository.findAll().stream().map(mapper::toDTO).toList();
    }
}
