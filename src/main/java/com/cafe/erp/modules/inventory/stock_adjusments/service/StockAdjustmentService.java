package com.cafe.erp.modules.inventory.stock_adjusments.service;

import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentCreateRequest;
import com.cafe.erp.modules.inventory.stock_adjusments.dto.StockAdjustmentResponseDTO;

import java.util.List;

public interface StockAdjustmentService {

    StockAdjustmentResponseDTO createAdjustment(StockAdjustmentCreateRequest request);

    StockAdjustmentResponseDTO getById(Long id);

    List<StockAdjustmentResponseDTO> getAll();
}
