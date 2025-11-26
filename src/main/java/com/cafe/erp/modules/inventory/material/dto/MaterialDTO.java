package com.cafe.erp.modules.inventory.material.dto;


import com.cafe.erp.common.enums.MaterialType;
import lombok.Data;

@Data
public class MaterialDTO {
    private Long materialId;
    private String name;
    private String sku;
    private Long categoryId;
    private String categoryName;
    private String uomCode;
    private MaterialType materialType;
    private Double unitCost;
    private Double reorderLevel;
    private Boolean isActive;
}

