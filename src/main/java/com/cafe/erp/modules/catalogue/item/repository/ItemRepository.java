package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByIsDeletedFalse();
    Optional<Item> findByIdAndIsDeletedFalse(Long id);
}
