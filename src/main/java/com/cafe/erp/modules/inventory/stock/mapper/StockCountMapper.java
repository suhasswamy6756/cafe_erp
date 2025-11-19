package com.cafe.erp.modules.inventory.stock.mapper;

import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.inventory.stock.dto.StockCountCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockCountDTO;
import com.cafe.erp.modules.inventory.stock.entity.StockCount;
import org.springframework.stereotype.Component;

@Component
public class StockCountMapper {

    public StockCountDTO toDTO(StockCount sc) {
        StockCountDTO dto = new StockCountDTO();
        dto.setCountId(sc.getCountId());
        dto.setLocationId(sc.getLocation().getLocationId());
        dto.setDate(sc.getDate());
        dto.setCountedBy(sc.getCountedBy());
        dto.setStatus(sc.getStatus());
        return dto;
    }

    public StockCount toEntity(StockCountCreateRequest req, Location location) {
        StockCount sc = new StockCount();
        sc.setLocation(location);
        sc.setDate(req.getDate());
        sc.setCountedBy(req.getCountedBy());
        sc.setStatus(req.getStatus());
        return sc;
    }
}
