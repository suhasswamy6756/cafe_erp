package com.cafe.erp.modules.inventory.categories.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryCategoryDTO {
    private Long categoryId;
    private String name;
    private String description;
    private Boolean isActive;
}

