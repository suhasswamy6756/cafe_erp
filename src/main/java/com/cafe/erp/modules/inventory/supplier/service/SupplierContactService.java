package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.SupplierContactCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierContactDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierContactUpdateRequest;

import java.util.List;


public interface SupplierContactService {

    SupplierContactDTO create(SupplierContactCreateRequest req);

    SupplierContactDTO update(Long contactId, SupplierContactUpdateRequest req);

    SupplierContactDTO get(Long contactId);

    List<SupplierContactDTO> listBySupplier(Long supplierId);

    void softDelete(Long contactId);
}
