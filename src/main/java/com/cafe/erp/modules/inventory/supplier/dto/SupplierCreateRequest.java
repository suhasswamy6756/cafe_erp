package com.cafe.erp.modules.inventory.supplier.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class SupplierCreateRequest {

    @NotBlank(message = "Supplier name is required")
    private String name;

    private String note;

    // Nested Contacts
    @NotNull(message = "Contacts list cannot be null")
    private List<SupplierContactCreateRequest> contacts;

    // Nested Addresses
    @NotNull(message = "Addresses list cannot be null")
    private List<SupplierAddressCreateRequest> addresses;
}


