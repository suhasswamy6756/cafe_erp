package com.cafe.erp.modules.inventory.material.service.implementation;

import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import com.cafe.erp.modules.inventory.categories.repository.InventoryCategoryRepository;
import com.cafe.erp.modules.inventory.material.dto.MaterialCreateRequest;
import com.cafe.erp.modules.inventory.material.dto.MaterialDTO;
import com.cafe.erp.modules.inventory.material.dto.MaterialUpdateRequest;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.mapper.MaterialMapper;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.material.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.cafe.erp.common.utils.AuditUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepo;
    private final InventoryCategoryRepository categoryRepo;
    private final MaterialMapper mapper;

    @Override
    public MaterialDTO create(MaterialCreateRequest request) {
        InventoryCategory category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Material material = mapper.toEntity(request, category);
        Material saved = materialRepo.save(material);

        return mapper.toDTO(saved);
    }

    @Override
    public List<MaterialDTO> getAll() {
        return materialRepo.findAllActiveWithCategory()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MaterialDTO get(Long id) {
        Material m = materialRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

        return mapper.toDTO(m);
    }
    @Override
    public MaterialDTO update(Long id, MaterialUpdateRequest request) {
        Material material = materialRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

        InventoryCategory category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        material.setName(request.getName());
        material.setSku(request.getSku());
        material.setCategory(category);
        material.setUomCode(request.getUomCode());
        material.setMaterialType(request.getMaterialType());
        material.setUnitCost(request.getUnitCost());
        material.setReorderLevel(request.getReorderLevel());
        material.setIsActive(request.getIsActive());
        material.setUpdatedAt(LocalDateTime.now());
        material.setUpdatedBy(getCurrentUser());

        Material saved = materialRepo.save(material);

        return mapper.toDTO(saved);
    }
    @Override
    public void delete(Long id) {
        Material material = materialRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found"));

        material.setIsDeleted(true);
        material.setDeletedAt(LocalDateTime.now());
        material.setDeletedBy(getCurrentUser());

        materialRepo.save(material);
    }
}
