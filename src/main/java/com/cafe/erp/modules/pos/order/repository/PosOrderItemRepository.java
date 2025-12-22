package com.cafe.erp.modules.pos.order.repository;

import com.cafe.erp.modules.pos.order.entity.PosOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosOrderItemRepository extends JpaRepository<PosOrderItem, Long> {
}

