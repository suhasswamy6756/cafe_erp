package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierRequest;

import java.util.List;

public interface ItemSupplierService {

    MaterialSupplierDTO assign(MaterialSupplierRequest req);

    List<MaterialSupplierDTO> listByItem(Long itemId);

    List<MaterialSupplierDTO> listBySupplier(Long supplierId);

    void delete(Long id); // softly delete
}


