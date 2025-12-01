package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialSupplierDTO {
    private Long id;
    private Long itemId;
    private String itemName;
    private Long supplierId;
    private String supplierName;
//    private BigDecimal unitCost;
//    private Integer leadTimeDays;
//    private Boolean isPrimary;
}

