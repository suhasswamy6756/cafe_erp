package com.cafe.erp.modules.inventory.stock.service;

import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.stock.dto.StockCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockDTO;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import com.cafe.erp.modules.inventory.stock.mapper.StockMapper;
import com.cafe.erp.modules.inventory.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepo;
    private final MaterialRepository materialRepo;
    private final LocationsRepository locationRepo;
    private final StockMapper mapper;

    @Override
    public StockDTO createStock(StockCreateRequest req) {
        Material material = materialRepo.findById(req.getMaterialId())
                .orElseThrow(() -> new RuntimeException("Material not found"));

        Location location = locationRepo.findById(req.getLocationId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        Stock stock = mapper.toEntity(req, material, location);

        return mapper.toDTO(stockRepo.save(stock));
    }

    @Override
    public List<StockDTO> getStockByMaterial(Long materialId) {
        return stockRepo.findByMaterial_MaterialIdAndIsDeletedFalse(materialId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<StockDTO> getStockByLocation(Long locationId) {
        return stockRepo.findByLocation_LocationIdAndIsDeletedFalse(locationId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<StockDTO> getAll() {
        return stockRepo.findAll().stream()
                .filter(s -> !s.getIsDeleted())
                .map(mapper::toDTO)
                .toList();
    }
}

