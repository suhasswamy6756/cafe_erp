package com.cafe.erp.modules.inventory.goods_receipt_note.repository;


import com.cafe.erp.modules.inventory.goods_receipt_note.entity.GRNItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GRNItemRepository extends JpaRepository<GRNItem, Long> {
    List<GRNItem> findByGrnHeader_GrnId(Long grnId);
}

