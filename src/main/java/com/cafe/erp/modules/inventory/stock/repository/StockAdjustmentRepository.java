package com.cafe.erp.modules.inventory.stock.repository;


import com.cafe.erp.modules.inventory.stock.entity.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, Long> {

    List<StockAdjustment> findByItem_Id(Long itemId);

    List<StockAdjustment> findByAdjustedAtBetween(LocalDateTime start, LocalDateTime end);
}

