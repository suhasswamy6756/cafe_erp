package com.cafe.erp.modules.inventory.stock_transfer.service;

import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferCreateRequest;
import com.cafe.erp.modules.inventory.stock_transfer.dto.StockTransferResponseDTO;

import java.util.List;

public interface StockTransferService {
    public StockTransferResponseDTO createStockTransfer(StockTransferCreateRequest request);

    public StockTransferResponseDTO getStockTransfer(Long id);

    public List<StockTransferResponseDTO> getAllStockTransfers();

    public StockTransferResponseDTO approveTransfer(Long id);
    public StockTransferResponseDTO issueTransfer(Long id);
    public StockTransferResponseDTO receiveTransfer(Long id);
    public StockTransferResponseDTO cancelTransfer(Long id);
    public StockTransferResponseDTO closeTransfer(Long id);
}
