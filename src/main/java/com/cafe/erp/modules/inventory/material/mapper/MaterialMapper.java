package com.cafe.erp.modules.inventory.material.mapper;


import com.cafe.erp.common.enums.MaterialType;
import com.cafe.erp.modules.inventory.categories.entity.InventoryCategory;
import com.cafe.erp.modules.inventory.material.dto.MaterialUpdateRequest;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.dto.MaterialDTO;
import com.cafe.erp.modules.inventory.material.dto.MaterialCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public Material toEntity(MaterialCreateRequest req, InventoryCategory category) {
        return Material.builder()
                .name(req.getName())
                .sku(req.getSku())
                .category(category)
                .uomCode(req.getUomCode())
                .materialType(MaterialType.valueOf(req.getMaterialType().name()))
                .unitCost(req.getUnitCost())
                .reorderLevel(req.getReorderLevel())
                .isActive(req.getIsActive())
                .build();
    }

    public Material toEntity(MaterialUpdateRequest req, InventoryCategory category) {
        return Material.builder()
                .name(req.getName())
                .sku(req.getSku())
                .category(category)
                .uomCode(req.getUomCode())
                .materialType(MaterialType.valueOf(req.getMaterialType().name()))
                .unitCost(req.getUnitCost())
                .reorderLevel(req.getReorderLevel())
                .isActive(req.getIsActive())
                .build();
    }

    public MaterialDTO toDTO(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setMaterialId(material.getMaterialId());
        dto.setName(material.getName());
        dto.setSku(material.getSku());
        dto.setCategoryId(material.getCategory().getCategoryId());
        dto.setCategoryName(material.getCategory().getName());
        dto.setUomCode(material.getUomCode());
        dto.setMaterialType(MaterialType.valueOf(material.getMaterialType().name()));
        dto.setUnitCost(material.getUnitCost());
        dto.setReorderLevel(material.getReorderLevel());
        dto.setIsActive(material.getIsActive());
        return dto;
    }
}
