package com.cafe.erp.modules.catalogue.discount.repository;


import com.cafe.erp.modules.catalogue.discount.entity.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDiscountRepository extends JpaRepository<ItemDiscount, Long> {

    List<ItemDiscount> findAllByDiscount_DiscountIdAndIsDeletedFalse(Long discountId);

    List<ItemDiscount> findAllByItem_IdAndIsDeletedFalse(Long itemId);
}

