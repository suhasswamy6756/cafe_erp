package com.cafe.erp.modules.catalogue.modifier_group.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifierGroupResponseDTO {

    private Long id;

    private String type;
    private String title;
    private String shortName;
    private String handle;
    private String description;

    private Integer sortOrder;
    private Boolean isActive;

    // Nested options
    private List<ModifierOptionResponseDTO> options;

    // Audit fields
    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

