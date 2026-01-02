package com.cafe.erp.modules.inventory.categories.repository;


import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryCategoryRepository extends JpaRepository<InventoryCategory, Long> {

    List<InventoryCategory> findAllByIsDeletedFalse();
}

