package com.cafe.erp.modules.inventory.stock_transfer.mapper;

import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferItemDTO;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferResponseDTO;
import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferHeader;
import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StockTransferMapper {

    public StockTransferResponseDTO toDTO(StockTransferHeader entity) {

        StockTransferResponseDTO dto = new StockTransferResponseDTO();

        dto.setTransferId(entity.getTransferId());
        dto.setRemarks(entity.getRemarks());
        dto.setTransferNumber(entity.getTransferNumber());
        dto.setStatus(entity.getStatus());

        dto.setFromLocationId(entity.getFromLocation().getLocationId());
//        dto.setFromLocationName(entity.getFromLocation().getName());

        dto.setToLocationId(entity.getToLocation().getLocationId());
//        dto.setToLocationName(entity.getToLocation().getName());

//        dto.setCreatedAt(entity.getCreatedAt());
//        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setItems(entity.getItems().stream().map(this::toItemDTO).collect(Collectors.toList()));

        return dto;
    }


    private StockTransferItemDTO toItemDTO(StockTransferItem item) {
        StockTransferItemDTO dto = new StockTransferItemDTO();

        dto.setTransferItemId(item.getTransferItemId());
        dto.setMaterialId(item.getMaterial().getMaterialId());
        dto.setMaterialName(item.getMaterial().getName());

        dto.setUomCode(item.getUomCode());
        dto.setRequestedQty(item.getRequestedQty());
        dto.setIssuedQty(item.getIssuedQty());
        dto.setShippedQty(item.getShippedQty());
        dto.setReceivedQty(item.getReceivedQty());

        return dto;
    }
}
