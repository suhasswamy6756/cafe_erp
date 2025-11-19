package com.cafe.erp.modules.inventory.supplier.dto;


import lombok.Data;

@Data
public class ItemSupplierRequest {
    private Long itemId;
    private Long supplierId;
}

