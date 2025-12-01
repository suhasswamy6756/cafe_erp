package com.cafe.erp.modules.inventory.stock_adjusments.repository;


import com.cafe.erp.modules.inventory.stock_adjusments.entity.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, Long> {
}

