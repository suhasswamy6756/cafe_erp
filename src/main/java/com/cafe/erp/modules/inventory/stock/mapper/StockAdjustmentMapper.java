package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.modules.inventory.stock.dto.StockAdjustmentCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockAdjustmentDTO;
import com.cafe.erp.modules.inventory.stock.entity.StockAdjustment;
import com.cafe.erp.modules.catalogue.item.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class StockAdjustmentMapper {

    public StockAdjustmentDTO toDTO(StockAdjustment sa) {
        StockAdjustmentDTO dto = new StockAdjustmentDTO();
        dto.setAdjustmentId(sa.getAdjustmentId());
        dto.setItemId(sa.getItem().getId());
        dto.setUomCode(sa.getUomCode());
        dto.setAdjustmentType(sa.getAdjustmentType());
        dto.setEffect(sa.getEffect());
        dto.setReason(sa.getReason());
        dto.setQuantity(sa.getQuantity());
        dto.setAdjustedBy(sa.getAdjustedBy());
        dto.setAdjustedAt(sa.getAdjustedAt());
        return dto;
    }

    public StockAdjustment toEntity(StockAdjustmentCreateRequest req, Item item) {
        StockAdjustment sa = new StockAdjustment();
        sa.setItem(item);
        sa.setUomCode(req.getUomCode());
        sa.setAdjustmentType(req.getAdjustmentType());
        sa.setEffect(req.getEffect());
        sa.setReason(req.getReason());
        sa.setQuantity(req.getQuantity());
        sa.setAdjustedBy(req.getAdjustedBy());
        return sa;
    }
}
