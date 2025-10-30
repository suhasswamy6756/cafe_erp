package com.cafe.erp.catalogue.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifierDTO {
    private Long id;
    private String title;
    private String shortName;
    private String foodType;
    private Double defaultSalePrice;
    private Integer sortOrder;
    private Boolean isDefault;
    private Boolean active;
    private Long modifierGroupId; // ✅ Only the ID
}
