package com.cafe.erp.modules.inventory.material.dto;


import com.cafe.erp.common.enums.MaterialType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterialCreateRequest {
    private String name;
    private String sku;
    private Long categoryId;
    private String uomCode;
    private MaterialType materialType;
    private BigDecimal unitCost;
    private Double reorderLevel;
    private Boolean isActive;
}

