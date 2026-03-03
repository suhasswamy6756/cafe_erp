package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
    Optional<ItemPrice> findByItem_IdAndLocation_LocationId(Long itemId, Long locationId);
    boolean existsByItem_IdAndLocation_LocationId(Long itemId, Long locationId);
}
