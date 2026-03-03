package com.cafe.erp.modules.inventory.supplier.dto;


import lombok.Data;

@Data
public class SupplierAddressUpdateRequest {

    private String line1;
    private String line2;
    private String postcode;
    private String city;
    private String state;
    private String country;
}

