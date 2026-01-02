package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.Data;
import java.util.List;

@Data
public class SupplierDTO {
    private Long supplierId;
    private String name;
    private String note;

    private List<SupplierContactDTO> contacts;
    private List<SupplierAddressDTO> addresses;
}


