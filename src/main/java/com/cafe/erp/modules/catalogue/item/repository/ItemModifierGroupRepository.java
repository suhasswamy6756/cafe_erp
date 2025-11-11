package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.entity.ItemModifierGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemModifierGroupRepository extends JpaRepository<ItemModifierGroup, Long> {

    List<ItemModifierGroup> findAllByItemAndIsDeletedFalse(Item item);

    void deleteAllByItem(Item item);
}
