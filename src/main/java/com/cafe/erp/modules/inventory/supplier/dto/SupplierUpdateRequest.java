package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierUpdateRequest {
    private String name;
    private String note;
    private List<SupplierContactCreateRequest> contacts;
    private List<SupplierAddressCreateRequest> addresses;
}

