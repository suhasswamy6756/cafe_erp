package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.Data;

@Data
public class SupplierContactUpdateRequest {
    private String contactName;
    private String designation;
    private String contactNumber;
    private String email;
}
