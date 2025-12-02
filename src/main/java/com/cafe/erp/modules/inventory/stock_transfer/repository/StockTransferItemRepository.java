package com.cafe.erp.modules.inventory.stock_transfer.repository;

import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockTransferItemRepository extends JpaRepository<StockTransferItem, Long> {
}
