package com.cafe.erp.modules.inventory.supplier.repository;

import com.cafe.erp.modules.inventory.supplier.entity.SupplierAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierAddressRepository extends JpaRepository<SupplierAddress, Long> {
    List<SupplierAddress> findBySupplier_SupplierIdAndIsDeletedFalse(Long supplierId);

    void deleteBySupplier_SupplierId(Long supplierId);
}

