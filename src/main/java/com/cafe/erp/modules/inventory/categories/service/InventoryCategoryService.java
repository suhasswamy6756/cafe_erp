package com.cafe.erp.modules.inventory.categories.service;



import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryCreateRequest;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryDTO;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryUpdateRequest;

import java.util.List;

public interface InventoryCategoryService {

    InventoryCategoryDTO create(InventoryCategoryCreateRequest req);

    InventoryCategoryDTO update(Long id, InventoryCategoryUpdateRequest req);

    InventoryCategoryDTO get(Long id);

    List<InventoryCategoryDTO> getAll();

    void delete(Long id);
}

