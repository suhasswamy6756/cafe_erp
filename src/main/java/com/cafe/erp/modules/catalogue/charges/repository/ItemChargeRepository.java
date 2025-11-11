package com.cafe.erp.modules.catalogue.charges.repository;


import com.cafe.erp.modules.catalogue.charges.entity.ItemCharge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemChargeRepository extends JpaRepository<ItemCharge, Long> {

    List<ItemCharge> findAllByCharge_ChargeIdAndIsDeletedFalse(Long chargeId);

    List<ItemCharge> findAllByItem_IdAndIsDeletedFalse(Long itemId);

    boolean existsByItem_IdAndCharge_ChargeId(Long itemId, Long chargeId);
}

