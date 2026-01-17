package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long> {
    ItemPrice findByItem_IdAndLocation_LocationId(Long itemId, Long locationId);
    boolean existsByItem_IdAndLocation_LocationId(Long itemId, Long locationId);
}
