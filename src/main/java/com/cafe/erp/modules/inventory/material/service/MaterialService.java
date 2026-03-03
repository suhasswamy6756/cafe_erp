package com.cafe.erp.modules.inventory.material.service;

import com.cafe.erp.modules.inventory.material.dto.MaterialCreateRequest;
import com.cafe.erp.modules.inventory.material.dto.MaterialDTO;
import com.cafe.erp.modules.inventory.material.dto.MaterialUpdateRequest;

import java.util.List;

public interface MaterialService {

    MaterialDTO create(MaterialCreateRequest req);
    MaterialDTO update(Long id, MaterialUpdateRequest req);
    MaterialDTO get(Long id);
    List<MaterialDTO> getAll();
    void delete(Long id);

}
