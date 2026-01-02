package com.cafe.erp.modules.inventory.supplier.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class SupplierContactRequest {

    @NotBlank(message = "Contact name is required")
    private String contactName;

    private String designation;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @Email(message = "Invalid email address")
    private String email;
}

