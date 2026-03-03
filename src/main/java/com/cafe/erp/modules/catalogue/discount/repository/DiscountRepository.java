package com.cafe.erp.modules.catalogue.discount.repository;


import com.cafe.erp.modules.catalogue.discount.entity.Discounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discounts, Long> {

    boolean existsByName(String name);
    boolean existsByHandle(String handle);

    List<Discounts> findAllByIsDeletedFalse();
    Optional<Discounts> findByDiscountIdAndIsDeletedFalse(Long id);
}

