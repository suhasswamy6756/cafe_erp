package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxUpdateRequest;

import java.util.List;

public interface SupplierTaxService {

    SupplierTaxDTO create(SupplierTaxCreateRequest req);

    SupplierTaxDTO update(Long id, SupplierTaxUpdateRequest req);

    SupplierTaxDTO get(Long id);

    List<SupplierTaxDTO> listBySupplier(Long supplierId);

    void softDelete(Long id);
}


