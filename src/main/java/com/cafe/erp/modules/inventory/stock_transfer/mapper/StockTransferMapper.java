package com.cafe.erp.modules.inventory.stock_transfer.mapper;

import com.cafe.erp.common.enums.StockTransferStatus;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferItemDTO;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferResponseDTO;
import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferHeader;
import org.springframework.stereotype.Component;

@Component
public class StockTransferMapper {

    public StockTransferResponseDTO toDTO(StockTransferHeader entity) {
        StockTransferResponseDTO dto = new StockTransferResponseDTO();
        dto.setTransferId(entity.getTransferId());
        dto.setTransferNumber(entity.getTransferNumber());
        dto.setFromLocationId(entity.getFromLocation().getLocationId());
        dto.setToLocationId(entity.getToLocation().getLocationId());
        dto.setStatus(StockTransferStatus.valueOf(entity.getStatus().name()));

        dto.setItems(entity.getItems().stream().map(item -> {
            StockTransferItemDTO i = new StockTransferItemDTO();
            i.setTransferItemId(item.getTransferItemId());
            i.setMaterialId(item.getMaterial().getMaterialId());
            i.setMaterialName(item.getMaterial().getName());
            i.setUomCode(item.getUomCode());
            i.setRequestedQty(item.getRequestedQty());
            i.setShippedQty(item.getShippedQty());
            i.setReceivedQty(item.getReceivedQty());
            return i;
        }).toList());

        return dto;
    }
}

