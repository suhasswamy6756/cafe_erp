package com.cafe.erp.modules.inventory.purchase.service;


import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderCreateRequest;
import com.cafe.erp.modules.inventory.purchase.dto.PurchaseOrderDTO;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderDTO createPurchaseOrder(PurchaseOrderCreateRequest request);

    List<PurchaseOrderDTO> getAll();

    PurchaseOrderDTO getById(Long id);

    PurchaseOrderDTO submit(Long id);

    PurchaseOrderDTO approve(Long id);
}

