package com.cafe.erp.modules.inventory.supplier.repository;

import com.cafe.erp.modules.inventory.supplier.entity.Uom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UomRepository extends JpaRepository<Uom, String> {
    List<Uom> findByIsDeletedFalse();


}
