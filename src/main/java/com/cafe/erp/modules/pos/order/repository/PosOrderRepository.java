package com.cafe.erp.modules.pos.order.repository;


import com.cafe.erp.modules.pos.order.entity.PosOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosOrderRepository extends JpaRepository<PosOrder, Long> {}

