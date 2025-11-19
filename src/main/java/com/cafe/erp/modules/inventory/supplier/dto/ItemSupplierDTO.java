package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSupplierDTO {
    private Long id;
    private Long itemId;
    private String itemName;
    private Long supplierId;
    private String supplierName;
}

