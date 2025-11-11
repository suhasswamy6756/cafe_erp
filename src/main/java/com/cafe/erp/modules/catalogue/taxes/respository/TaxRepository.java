package com.cafe.erp.modules.catalogue.taxes.respository;


import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    List<Tax> findAllByIsDeletedFalse();

    Optional<Tax> findByTaxIdAndIsDeletedFalse(Long taxId);

    boolean existsByName(String name);

    boolean existsByHandle(String handle);
}
