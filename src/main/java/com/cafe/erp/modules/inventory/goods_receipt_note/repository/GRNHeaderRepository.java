package com.cafe.erp.modules.inventory.goods_receipt_note.repository;

import com.cafe.erp.modules.inventory.goods_receipt_note.entity.GRNHeader;
import com.cafe.erp.common.enums.GRNStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GRNHeaderRepository extends JpaRepository<GRNHeader, Long> {
    List<GRNHeader> findByStatus(GRNStatus status);
}

