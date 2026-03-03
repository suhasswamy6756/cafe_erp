package com.cafe.erp.modules.catalogue.modifier_group.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifierOptionRequestDTO {

    private String name;
    private Double priceModifier;
    private Integer sortOrder;
    private Boolean isActive;
}

