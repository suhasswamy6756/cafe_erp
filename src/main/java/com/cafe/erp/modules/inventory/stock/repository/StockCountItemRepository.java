package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.StockCountItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockCountItemRepository extends JpaRepository<StockCountItem, Long> {

    List<StockCountItem> findByCount_CountId(Long countId);

    List<StockCountItem> findByItem_ItemId(Long itemId);
}

