package com.cafe.erp.modules.catalogue.taxes.dto;

import com.cafe.erp.common.enums.TaxType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaxResponseDTO {

    private Long taxId;
    private String name;
    private String handle;
    private String description;

    private TaxType type;
    private BigDecimal value;
    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private List<Long> itemIds; // ✅ return associated item IDs
}


