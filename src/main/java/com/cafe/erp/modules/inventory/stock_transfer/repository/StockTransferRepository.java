package com.cafe.erp.modules.inventory.stock_transfer.repository;

import com.cafe.erp.modules.inventory.stock_transfer.entity.StockTransferHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface StockTransferRepository extends JpaRepository<StockTransferHeader, Long> {

}
