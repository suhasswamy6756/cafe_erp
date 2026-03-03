package com.cafe.erp.modules.inventory.categories.dto;


import lombok.Data;

@Data
public class InventoryCategoryCreateRequest {
    private String name;
    private String description;
    private Boolean isActive;
}
