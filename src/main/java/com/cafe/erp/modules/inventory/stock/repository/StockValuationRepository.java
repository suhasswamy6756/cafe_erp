package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.StockValuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockValuationRepository extends JpaRepository<StockValuation, Long> {

    List<StockValuation> findByItem_ItemId(Long itemId);

    List<StockValuation> findByLocation_LocationId(Long locationId);

    StockValuation findTopByItem_ItemIdAndLocation_LocationIdOrderByValuationDateDesc(
            Long itemId, Long locationId
    );
}

