package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.common.enums.StockStatus;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.stock.dto.StockCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockDTO;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public Stock toEntity(StockCreateRequest req, Material material, Location location) {
        return Stock.builder()
                .material(material)
                .location(location)
                .uomCode(req.getUomCode())
                .batchNo(req.getBatchNo())
                .expiryDate(req.getExpiryDate())
                .quantity(req.getQuantity())
                .unitCost(req.getUnitCost())
                .stockStatus(StockStatus.valueOf(req.getStockStatus().name()))
                .build();
    }

    public StockDTO toDTO(Stock stock) {
        StockDTO dto = new StockDTO();
        dto.setStockId(stock.getStockId());
        dto.setMaterialId(stock.getMaterial().getMaterialId());
        dto.setMaterialName(stock.getMaterial().getName());
        dto.setLocationId(stock.getLocation().getLocationId());
        dto.setLocationName(stock.getLocation().getName());
        dto.setUomCode(stock.getUomCode());
        dto.setQuantity(stock.getQuantity());
        dto.setUnitCost(stock.getUnitCost());
        dto.setBatchNo(stock.getBatchNo());
        dto.setExpiryDate(stock.getExpiryDate());
        dto.setStockStatus(StockStatus.valueOf(stock.getStockStatus().name()));
        return dto;
    }
}

