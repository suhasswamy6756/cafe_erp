package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.StockChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockChangeLogRepository extends JpaRepository<StockChangeLog, Long> {

    List<StockChangeLog> findByItem_Id(Long itemId);

    List<StockChangeLog> findByLocation_LocationId(Long locationId);

    List<StockChangeLog> findByChangeTimestampBetween(LocalDateTime start, LocalDateTime end);
}

