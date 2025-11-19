package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.inventory.stock.dto.StockCountItemCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockCountItemDTO;
import com.cafe.erp.modules.inventory.stock.entity.StockCount;
import com.cafe.erp.modules.inventory.stock.entity.StockCountItem;
import org.springframework.stereotype.Component;

@Component
public class StockCountItemMapper {

    public StockCountItemDTO toDTO(StockCountItem sci) {
        StockCountItemDTO dto = new StockCountItemDTO();
        dto.setId(sci.getId());
        dto.setCountId(sci.getCount().getCountId());
        dto.setItemId(sci.getItem().getId());
        dto.setExpectedQty(sci.getExpectedQty());
        dto.setActualQty(sci.getActualQty());
        dto.setVariance(sci.getVariance());
        return dto;
    }

    public StockCountItem toEntity(StockCountItemCreateRequest req, StockCount count, Item item) {
        StockCountItem sci = new StockCountItem();
        sci.setCount(count);
        sci.setItem(item);
        sci.setExpectedQty(req.getExpectedQty());
        sci.setActualQty(req.getActualQty());
        sci.setVariance(req.getActualQty().subtract(req.getExpectedQty()));
        return sci;
    }
}
