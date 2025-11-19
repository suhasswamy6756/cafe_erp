package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByItem_ItemIdAndIsDeletedFalse(Long itemId);

    List<Stock> findByLocation_LocationIdAndIsDeletedFalse(Long locationId);

    List<Stock> findByItem_ItemIdAndLocation_LocationIdAndIsDeletedFalse(Long itemId, Long locationId);
}
