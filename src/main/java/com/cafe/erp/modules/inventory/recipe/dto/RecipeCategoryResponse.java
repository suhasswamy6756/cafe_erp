package com.cafe.erp.modules.inventory.recipe.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecipeCategoryResponse {
    private Long recipeCategoryId;
    private String recipeCategoryName;
    private String recipeCategoryDescription;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
