package com.cafe.erp.modules.inventory.stock_adjusments.service.implementation;


import com.cafe.erp.common.enums.StockStatus;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import com.cafe.erp.modules.inventory.stock.repository.StockRepository;
import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentCreateRequest;
import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentResponseDTO;
import com.cafe.erp.modules.inventory.stock_adjusments.entity.StockAdjustment;
import com.cafe.erp.modules.inventory.stock_adjusments.repository.StockAdjustmentRepository;
import com.cafe.erp.modules.inventory.stock_adjusments.service.StockAdjustmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Transactional
public class StockAdjustmentServiceImpl implements StockAdjustmentService {

    private final StockRepository stockRepo;
    private final StockAdjustmentRepository adjustmentRepo;
    private final MaterialRepository materialRepo;
    private final LocationsRepository locationRepo;

    @Override
    public StockAdjustmentResponseDTO createAdjustment(StockAdjustmentCreateRequest req) {

        // Validate Material Exists


        // Validate Today Stock
        // Fetch material
        Material material = materialRepo.findById(req.getMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

// Fetch existing stock for that material at location
        Stock stock = stockRepo
                .findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(req.getMaterialId(), req.getLocationId())
                .orElseGet(() -> Stock.builder()
                        .material(material)
                        .location(locationRepo.findById(req.getLocationId())
                                .orElseThrow(() -> new ResourceNotFoundException("Location not found")))
                        .quantity(BigDecimal.ZERO)
                        .uomCode(req.getUomCode())
                        .stockStatus(StockStatus.AVAILABLE)
                        .build()
                );

// Perform adjustment
        if (req.getAdjustmentType().equalsIgnoreCase("INCREASE")) {
            stock.setQuantity(stock.getQuantity().add(req.getQuantity()));
        } else if (req.getAdjustmentType().equalsIgnoreCase("DECREASE")) {
            if (stock.getQuantity().compareTo(req.getQuantity()) < 0) {
                throw new IllegalArgumentException("Stock cannot go negative");
            }
            stock.setQuantity(stock.getQuantity().subtract(req.getQuantity()));
        }

        stockRepo.save(stock);


        // Save Adjustment Log
        StockAdjustment sa = new StockAdjustment();
        sa.setMaterialId(material.getMaterialId());
        sa.setUomCode(req.getUomCode());
        sa.setAdjustmentType(req.getAdjustmentType().toUpperCase());
        sa.setReason(req.getReason());
        sa.setQuantity(req.getQuantity());
        sa.setAdjustedBy(getCurrentUser());
        sa.setCreatedBy(getCurrentUser());
        sa.setCreatedAt(LocalDateTime.now());

        adjustmentRepo.save(sa);

        // Build Response
        StockAdjustmentResponseDTO response = new StockAdjustmentResponseDTO();
        response.setAdjustmentId(sa.getAdjustmentId());
        response.setMaterialId(material.getMaterialId());
        response.setMaterialName(material.getName());
        response.setAdjustmentType(sa.getAdjustmentType());
        response.setQuantity(sa.getQuantity());
        response.setUomCode(req.getUomCode());
        response.setAdjustedBy(sa.getAdjustedBy());
        response.setAdjustedAt(sa.getAdjustedAt());

        return response;
    }

    @Override
    public StockAdjustmentResponseDTO getById(Long id) {
        StockAdjustment adj = adjustmentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Stock adjustment not found"));

        Material material = materialRepo.findById(adj.getMaterialId()).orElseThrow();

        StockAdjustmentResponseDTO dto = new StockAdjustmentResponseDTO();
        dto.setAdjustmentId(adj.getAdjustmentId());
        dto.setMaterialId(material.getMaterialId());
        dto.setMaterialName(material.getName());
        dto.setAdjustmentType(adj.getAdjustmentType());
        dto.setQuantity(adj.getQuantity());
        dto.setUomCode(adj.getUomCode());
        dto.setAdjustedAt(adj.getAdjustedAt());

        return dto;
    }

    @Override
    public List<StockAdjustmentResponseDTO> getAll() {
        return adjustmentRepo.findAll().stream().map(adj -> {
            Material material = materialRepo.findById(adj.getMaterialId()).orElseThrow();
            StockAdjustmentResponseDTO dto = new StockAdjustmentResponseDTO();
            dto.setAdjustmentId(adj.getAdjustmentId());
            dto.setMaterialId(adj.getMaterialId());
            dto.setMaterialName(material.getName());
            dto.setAdjustmentType(adj.getAdjustmentType());
            dto.setQuantity(adj.getQuantity());
            dto.setAdjustedAt(adj.getAdjustedAt());
            return dto;
        }).toList();
    }
}

