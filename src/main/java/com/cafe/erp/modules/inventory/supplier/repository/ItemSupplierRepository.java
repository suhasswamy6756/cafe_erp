package com.cafe.erp.modules.inventory.supplier.repository;

import com.cafe.erp.modules.inventory.supplier.entity.ItemSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ItemSupplierRepository extends JpaRepository<ItemSupplier, Long> {

    List<ItemSupplier> findByItem_IdAndIsDeletedFalse(Long itemId);

    List<ItemSupplier> findBySupplier_SupplierIdAndIsDeletedFalse(Long supplierId);
}
