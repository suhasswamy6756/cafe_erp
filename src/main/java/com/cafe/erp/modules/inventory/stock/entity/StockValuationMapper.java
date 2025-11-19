package com.cafe.erp.modules.inventory.stock.entity;

import com.cafe.erp.modules.inventory.stock.dto.StockValuationDTO;
import org.springframework.stereotype.Component;

@Component
public class StockValuationMapper {

    public StockValuationDTO toDTO(StockValuation sv) {
        StockValuationDTO dto = new StockValuationDTO();
        dto.setValuationId(sv.getValuationId());
        dto.setItemId(sv.getItem().getId());
        dto.setLocationId(sv.getLocation().getLocationId());
        dto.setWeightedAvgCost(sv.getWeightedAvgCost());
        dto.setTotalValue(sv.getTotalValue());
        dto.setValuationDate(sv.getValuationDate());
        return dto;
    }
}

