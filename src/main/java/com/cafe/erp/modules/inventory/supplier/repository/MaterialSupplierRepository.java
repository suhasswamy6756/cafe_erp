package com.cafe.erp.modules.inventory.supplier.repository;

import com.cafe.erp.modules.inventory.supplier.entity.MaterialSupplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface MaterialSupplierRepository extends JpaRepository<MaterialSupplier, Long> {

    List<MaterialSupplier> findByMaterial_MaterialIdAndIsDeletedFalse(Long materialId);

    List<MaterialSupplier> findBySupplier_SupplierIdAndIsDeletedFalse(Long supplierId);
}

