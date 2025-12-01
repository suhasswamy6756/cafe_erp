package com.cafe.erp.modules.inventory.stock.repository;

import com.cafe.erp.modules.inventory.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByMaterial_MaterialIdAndIsDeletedFalse(Long materialId);

    List<Stock> findByLocation_LocationIdAndIsDeletedFalse(Long locationId);

    List<Stock> findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
            Long materialId, Long locationId
    );
}

