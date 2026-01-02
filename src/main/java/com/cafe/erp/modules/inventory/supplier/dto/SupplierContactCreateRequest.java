package com.cafe.erp.modules.inventory.supplier.dto;


import lombok.Data;

@Data
public class SupplierContactCreateRequest {
    private Long supplierId;
    private String contactName;
    private String designation;
    private String contactNumber;
    private String email;
}

