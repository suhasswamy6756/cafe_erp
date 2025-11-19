package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierContact;
import org.springframework.stereotype.Component;

@Component
public class SupplierContactMapper {

    public SupplierContactDTO toDTO(SupplierContact c) {
        SupplierContactDTO dto = new SupplierContactDTO();
        dto.setContactId(c.getContactId());
        dto.setSupplierId(c.getSupplier().getSupplierId());
        dto.setContactName(c.getContactName());
        dto.setDesignation(c.getDesignation());
        dto.setContactNumber(c.getContactNumber());
        dto.setEmail(c.getEmail());
        return dto;
    }

    // CREATE
    public SupplierContact toEntity(Supplier supplier, SupplierContactCreateRequest req) {
        SupplierContact c = new SupplierContact();
        c.setSupplier(supplier);
        c.setContactName(req.getContactName());
        c.setDesignation(req.getDesignation());
        c.setContactNumber(req.getContactNumber());
        c.setEmail(req.getEmail());
        return c;
    }

    // UPDATE
    public void updateEntity(SupplierContact c, SupplierContactUpdateRequest req) {
        c.setContactName(req.getContactName());
        c.setDesignation(req.getDesignation());
        c.setContactNumber(req.getContactNumber());
        c.setEmail(req.getEmail());
    }
}
