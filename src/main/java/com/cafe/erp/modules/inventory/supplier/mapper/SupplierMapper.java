package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.SupplierCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierDTO;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    @Autowired
    private SupplierAddressMapper addressMapper;

    @Autowired
    private SupplierContactMapper contactMapper;

    public SupplierDTO toDTO(Supplier entity) {
        if (entity == null) return null;

        SupplierDTO dto = new SupplierDTO();
        dto.setSupplierId(entity.getSupplierId());
        dto.setName(entity.getName());
        dto.setNote(entity.getNote());

        // map contacts + addresses
        dto.setContacts(
                entity.getContacts()
                        .stream()
                        .map(contactMapper::toDTO)
                        .toList()
        );

        dto.setAddresses(
                entity.getAddresses()
                        .stream()
                        .map(addressMapper::toDTO)
                        .toList()
        );

        return dto;
    }

    public Supplier toEntity(SupplierCreateRequest req) {
        Supplier s = new Supplier();
        s.setName(req.getName());
        s.setNote(req.getNote());
        return s;
    }

    public void updateEntity(Supplier s, SupplierUpdateRequest req) {
        s.setName(req.getName());
        s.setNote(req.getNote());
    }
}

