package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialSupplierDTO {
    private Long id;
    private Long materialId;
    private String materialName;
    private Long supplierId;
    private String supplierName;
//    private BigDecimal unitCost;
//    private Integer leadTimeDays;
//    private Boolean isPrimary;
}

