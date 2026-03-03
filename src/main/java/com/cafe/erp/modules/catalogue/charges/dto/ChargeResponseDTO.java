package com.cafe.erp.modules.catalogue.charges.dto;


import com.cafe.erp.common.enums.ChargeType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ChargeResponseDTO {

    private Long chargeId;
    private String name;
    private String handle;
    private String description;

    private ChargeType type;
    private Double value;
    private Boolean isTaxable;
    private Boolean isActive;

    private List<Long> itemIds;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

