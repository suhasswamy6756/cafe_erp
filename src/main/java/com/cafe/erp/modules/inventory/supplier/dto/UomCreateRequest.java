package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UomCreateRequest {
    private String uomCode;
    private String description;
    private String baseUnit;
    private Double conversionFactor;
}

