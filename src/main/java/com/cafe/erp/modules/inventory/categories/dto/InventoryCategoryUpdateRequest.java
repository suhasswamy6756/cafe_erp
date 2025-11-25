package com.cafe.erp.modules.inventory.categories.dto;


import lombok.Data;

@Data
public class InventoryCategoryUpdateRequest {
    private String name;
    private String description;
    private Boolean isActive;
}

