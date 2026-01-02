package com.cafe.erp.modules.inventory.supplier.service;

import com.cafe.erp.modules.inventory.supplier.dto.UomCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.UomDTO;
import com.cafe.erp.modules.inventory.supplier.dto.UomUpdateRequest;

import java.util.List;

public interface UomService {
    UomDTO create(UomCreateRequest req);
    UomDTO update(String uomCode, UomUpdateRequest req);
    void delete(String uomCode);
    UomDTO get(String uomCode);
    List<UomDTO> getAll();
}

