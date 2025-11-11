package com.cafe.erp.modules.catalogue.modifier_group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifierGroupRequestDTO {

    private String type;

    private String title;
    private String shortName;
    private String handle;
    private String description;

    private Integer sortOrder;

    private Boolean isActive;

    // Create/Update options along with group
    private List<ModifierOptionRequestDTO> options;
}
