package com.cafe.erp.modules.inventory.material.dto;

import com.cafe.erp.common.enums.MaterialType;
import lombok.Data;

@Data
public class MaterialUpdateRequest {
    private String name;
    private String sku;
    private Long categoryId;
    private String uomCode;
    private MaterialType materialType;
    private Double unitCost;
    private Double reorderLevel;
    private Boolean isActive;
}
