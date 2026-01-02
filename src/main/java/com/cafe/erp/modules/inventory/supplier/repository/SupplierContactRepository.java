package com.cafe.erp.modules.inventory.supplier.repository;

import com.cafe.erp.modules.inventory.supplier.entity.SupplierContact;
import org.springframework.data.jpa.repository.JpaRepository;




import java.util.List;

public interface SupplierContactRepository extends JpaRepository<SupplierContact, Long> {
    List<SupplierContact> findBySupplier_SupplierIdAndIsDeletedFalse(Long supplierId);

    void deleteBySupplier_SupplierId(Long supplierId);
}

