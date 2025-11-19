package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.modules.inventory.stock.dto.StockChangeLogDTO;
import com.cafe.erp.modules.inventory.stock.entity.StockChangeLog;
import org.springframework.stereotype.Component;

@Component
public class StockChangeLogMapper {

    public StockChangeLogDTO toDTO(StockChangeLog log) {
        StockChangeLogDTO dto = new StockChangeLogDTO();
        dto.setChangeId(log.getChangeId());
        dto.setItemId(log.getItem().getId());
        dto.setLocationId(log.getLocation().getLocationId());
        dto.setChangeType(log.getChangeType());
        dto.setOldQuantity(log.getOldQuantity());
        dto.setNewQuantity(log.getNewQuantity());
        dto.setQuantityDiff(log.getQuantityDiff());
        dto.setCostPerUom(log.getCostPerUom());
        dto.setChangeTimestamp(log.getChangeTimestamp());
        return dto;
    }
}

