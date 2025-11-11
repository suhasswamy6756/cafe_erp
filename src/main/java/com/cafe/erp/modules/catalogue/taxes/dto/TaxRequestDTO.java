package com.cafe.erp.modules.catalogue.taxes.dto;

import com.cafe.erp.common.enums.TaxType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TaxRequestDTO {
    private String name;
    private String handle;
    private String description;
    private TaxType type;      // PERCENTAGE / FIXED
    private BigDecimal value;
    private Boolean isInclusive;
    private Boolean isActive;
    private List<Long> itemIds;
}
