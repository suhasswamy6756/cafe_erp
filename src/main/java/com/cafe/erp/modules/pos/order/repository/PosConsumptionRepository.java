package com.cafe.erp.modules.pos.order.repository;

import com.cafe.erp.modules.pos.order.entity.PosConsumption;
import com.cafe.erp.modules.pos.order.entity.PosOrder;
import com.cafe.erp.modules.pos.order.entity.PosOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosConsumptionRepository extends JpaRepository<PosConsumption, Long> {
    List<PosConsumption> findByOrderItem(PosOrderItem orderItem);
    List<PosConsumption> findByOrder(PosOrder order);
}

