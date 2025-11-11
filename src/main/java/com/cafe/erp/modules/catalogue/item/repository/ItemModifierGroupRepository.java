package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.Item;
import com.cafe.erp.modules.catalogue.item.entity.ItemModifierGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemModifierGroupRepository extends JpaRepository<ItemModifierGroup, Long> {

    List<ItemModifierGroup> findAllByItemAndIsDeletedFalse(Item item);

    void deleteAllByItem(Item item);

    @Query("SELECT img FROM ItemModifierGroup img WHERE img.item.id = :itemId")
    List<ItemModifierGroup> findAllByItemId(@Param("itemId") Long itemId);

    @Query("SELECT img FROM ItemModifierGroup img WHERE img.item.id = :itemId AND img.modifierGroup.id = :mgId")
    Optional<ItemModifierGroup> findByItemIdAndGroupId(Long itemId, Long mgId);

}
