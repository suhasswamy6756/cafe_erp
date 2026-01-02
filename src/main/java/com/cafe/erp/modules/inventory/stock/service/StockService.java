package com.cafe.erp.modules.inventory.stock.service;

import com.cafe.erp.modules.inventory.stock.dto.StockCreateRequest;
import com.cafe.erp.modules.inventory.stock.dto.StockDTO;

import java.util.List;

public interface StockService {
    StockDTO createStock(StockCreateRequest request);

    List<StockDTO> getStockByMaterial(Long materialId);

    List<StockDTO> getStockByLocation(Long locationId);

    List<StockDTO> getAll();
}

