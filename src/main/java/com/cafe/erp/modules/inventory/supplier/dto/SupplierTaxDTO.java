package com.cafe.erp.modules.inventory.supplier.dto;

import com.cafe.erp.common.enums.TaxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class SupplierTaxDTO {
    private Long id;
    private Long supplierId;
    private Long taxId;
    private String taxName;
    private String taxDescription;
    private TaxType taxType;
    private BigDecimal taxValue;
}


