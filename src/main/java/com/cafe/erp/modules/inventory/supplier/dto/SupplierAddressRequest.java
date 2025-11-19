package com.cafe.erp.modules.inventory.supplier.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierAddressRequest {

    @NotBlank(message = "Address line1 is required")
    private String line1;

    private String line2;

    @NotBlank(message = "Postcode is required")
    private String postcode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;
}

