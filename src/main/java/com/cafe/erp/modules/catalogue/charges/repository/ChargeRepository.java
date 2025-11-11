package com.cafe.erp.modules.catalogue.charges.repository;


import com.cafe.erp.modules.catalogue.charges.entity.Charges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charges, Long> {

    List<Charges> findAllByIsDeletedFalse();

    Optional<Charges> findByChargeIdAndIsDeletedFalse(Long id);

    boolean existsByName(String name);
    boolean existsByHandle(String handle);
}

