package com.cafe.erp.catalogue.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifierGroupDTO {
    private Long id;
    private String title;
    private String shortName;
    private String handle;
    private String groupType;
    private String description;
    private Integer sortOrder;
    private Boolean active;

    private List<ModifierDTO> modifiers;
}
