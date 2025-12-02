package com.cafe.erp.modules.inventory.supplier.dto;


import lombok.Data;

@Data
public class MaterialSupplierRequest {
    private Long materialId;
    private Long supplierId;
}

