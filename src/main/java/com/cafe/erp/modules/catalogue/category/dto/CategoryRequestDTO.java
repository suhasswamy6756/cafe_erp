package com.cafe.erp.modules.catalogue.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    private String name;
    private String shortName;
    private String handle;
    private String description;

    private Long parentCategoryId;
    private String kotGroup;
    private Long timingGroupId;

    private int sortOrder;
    private boolean active;
}

