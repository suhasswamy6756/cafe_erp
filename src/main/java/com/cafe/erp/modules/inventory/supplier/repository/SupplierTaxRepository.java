package com.cafe.erp.modules.inventory.supplier.repository;


import com.cafe.erp.modules.inventory.supplier.entity.SupplierMultipleTax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierTaxRepository extends JpaRepository<SupplierMultipleTax, Long> {

    List<SupplierMultipleTax> findBySupplier_SupplierIdAndIsDeletedFalse(Long supplierId);

}

