package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierAddressUpdateRequest;

import java.util.List;

public interface SupplierAddressService {

    SupplierAddressDTO create(SupplierAddressCreateRequest req);

    SupplierAddressDTO update(Long addressId, SupplierAddressUpdateRequest req);

    SupplierAddressDTO get(Long addressId);

    List<SupplierAddressDTO> listBySupplier(Long supplierId);

    void softDelete(Long addressId);
}


