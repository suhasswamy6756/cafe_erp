package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.SupplierCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierUpdateRequest;

import java.util.List;

public interface SupplierService {

    SupplierDTO createSupplier(SupplierCreateRequest request);

    SupplierDTO updateSupplier(Long supplierId, SupplierUpdateRequest request);

    SupplierDTO getSupplier(Long supplierId);

    List<SupplierDTO> getAllSuppliers();

    void deleteSupplier(Long supplierId);
}
