package com.cafe.erp.modules.inventory.supplier.dto;


import lombok.Data;

@Data
public class SupplierTaxCreateRequest {
    private Long supplierId;
    private Long taxId;
}

