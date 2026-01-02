package com.cafe.erp.modules.inventory.categories.mapper;


import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryCreateRequest;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryDTO;
import com.cafe.erp.modules.inventory.categories.dto.InventoryCategoryUpdateRequest;
import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import org.springframework.stereotype.Component;

@Component
public class InventoryCategoryMapper {

    public InventoryCategoryDTO toDTO(InventoryCategory c) {
        return InventoryCategoryDTO.builder()
                .categoryId(c.getCategoryId())
                .name(c.getName())
                .description(c.getDescription())
                .isActive(c.getIsActive())
                .build();
    }

    public InventoryCategory toEntity(InventoryCategoryCreateRequest req) {
        return InventoryCategory.builder()
                .name(req.getName())
                .description(req.getDescription())
                .isActive(req.getIsActive())
                .build();
    }

    public void updateEntity(InventoryCategory category, InventoryCategoryUpdateRequest req) {
        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIsActive(req.getIsActive());
    }
}

