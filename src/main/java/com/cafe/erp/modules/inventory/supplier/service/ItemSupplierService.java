package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.ItemSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.ItemSupplierRequest;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemSupplierService {

    ItemSupplierDTO assign(ItemSupplierRequest req);

    List<ItemSupplierDTO> listByItem(Long itemId);

    List<ItemSupplierDTO> listBySupplier(Long supplierId);

    void delete(Long id); // softly delete
}


