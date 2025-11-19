package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.StockCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockCountRepository extends JpaRepository<StockCount, Long> {

    List<StockCount> findByLocation_LocationIdAndIsDeletedFalse(Long locationId);

    List<StockCount> findByDateBetweenAndIsDeletedFalse(LocalDate start, LocalDate end);
}

