package com.cafe.erp.modules.catalogue.item.repository;

import com.cafe.erp.modules.catalogue.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByIsDeletedFalse();
}
