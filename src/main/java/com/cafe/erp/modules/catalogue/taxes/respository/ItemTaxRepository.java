package com.cafe.erp.modules.catalogue.taxes.respository;


import com.cafe.erp.modules.catalogue.taxes.entity.ItemTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTaxRepository extends JpaRepository<ItemTax, Long> {

    List<ItemTax> findAllByItemIdAndIsDeletedFalse(Long itemId);

    boolean existsByItemIdAndTax_TaxId(Long itemId, Long taxId);

    List<ItemTax> findAllByTax_TaxIdAndIsDeletedFalse(Long taxId);
}


