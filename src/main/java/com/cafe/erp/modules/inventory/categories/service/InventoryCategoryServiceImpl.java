package com.cafe.erp.modules.inventory.categories.service;

import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryCreateRequest;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryDTO;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryUpdateRequest;
import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import com.cafe.erp.modules.inventory.categories.mapper.InventoryCategoryMapper;
import com.cafe.erp.modules.inventory.categories.repository.InventoryCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryCategoryServiceImpl implements InventoryCategoryService {

    private final InventoryCategoryRepository repo;
    private final InventoryCategoryMapper mapper;

    @Override
    public InventoryCategoryDTO create(InventoryCategoryCreateRequest req) {
        InventoryCategory category = mapper.toEntity(req);
        repo.save(category);
        return mapper.toDTO(category);
    }

    @Override
    public InventoryCategoryDTO update(Long id, InventoryCategoryUpdateRequest req) {
        InventoryCategory c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        mapper.updateEntity(c, req);
        repo.save(c);
        return mapper.toDTO(c);
    }

    @Override
    public InventoryCategoryDTO get(Long id) {
        InventoryCategory c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return mapper.toDTO(c);
    }

    @Override
    public List<InventoryCategoryDTO> getAll() {
        return repo.findAllByIsDeletedFalse()
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        InventoryCategory c = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        c.setIsDeleted(true);
        c.setDeletedAt(LocalDateTime.now());
        c.setDeletedBy("system");

        repo.save(c);
    }
}
