package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.common.enums.StockStatus;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.inventory.stock.dto.StockCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockDTO;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {

    public StockDTO toDTO(Stock s) {
        StockDTO dto = new StockDTO();
        dto.setStockId(s.getStockId());
        dto.setItemId(s.getItem().getId());
        dto.setLocationId(s.getLocation().getLocationId());
        dto.setIsRawMaterial(s.getIsRawMaterial());
        dto.setUomCode(s.getUomCode());
        dto.setQuantity(s.getQuantity());
        dto.setUom(s.getUom());
        dto.setBatchNo(s.getBatchNo());
        dto.setExpiryDate(s.getExpiryDate());
        dto.setUnitCost(s.getUnitCost());
        dto.setStockStatus(s.getStockStatus().name());
        dto.setSourceType(s.getSourceType());
        dto.setSourceId(s.getSourceId());
        return dto;
    }

    public Stock toEntity(StockCreateRequest req, Item item, Location location) {
        Stock s = new Stock();
        s.setItem(item);
        s.setLocation(location);
        s.setIsRawMaterial(req.getIsRawMaterial());
        s.setUomCode(req.getUomCode());
        s.setQuantity(req.getQuantity());
        s.setUom(req.getUom());
        s.setBatchNo(req.getBatchNo());
        s.setExpiryDate(req.getExpiryDate());
        s.setUnitCost(req.getUnitCost());
        s.setStockStatus(StockStatus.valueOf(req.getStockStatus()));
        s.setSourceType(req.getSourceType());
        s.setSourceId(req.getSourceId());
        return s;
    }
}

