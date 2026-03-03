package com.cafe.erp.modules.catalogue.category.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryResponseDTO {

    private Long id;

    private String name;
    private String shortName;
    private String handle;
    private String description;

    private Long parentCategoryId;

    private String kotGroup;
    private Long timingGroupId;

    private int sortOrder;
    private boolean active;

    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
