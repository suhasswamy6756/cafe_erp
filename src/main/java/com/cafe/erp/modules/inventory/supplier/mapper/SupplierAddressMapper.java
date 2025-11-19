package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.*;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierAddress;
import org.springframework.stereotype.Component;

@Component
public class SupplierAddressMapper {

    public SupplierAddressDTO toDTO(SupplierAddress a) {
        SupplierAddressDTO dto = new SupplierAddressDTO();
        dto.setAddressId(a.getAddressId());
        dto.setSupplierId(a.getSupplier().getSupplierId());
        dto.setLine1(a.getLine1());
        dto.setLine2(a.getLine2());
        dto.setCity(a.getCity());
        dto.setState(a.getState());
        dto.setCountry(a.getCountry());
        dto.setPostcode(a.getPostcode());
        return dto;
    }

    public SupplierAddress toEntity(Supplier supplier, SupplierAddressCreateRequest req) {
        SupplierAddress a = new SupplierAddress();
        a.setSupplier(supplier);
        a.setLine1(req.getLine1());
        a.setLine2(req.getLine2());
        a.setCity(req.getCity());
        a.setState(req.getState());
        a.setCountry(req.getCountry());
        a.setPostcode(req.getPostcode());
        return a;
    }

    public void updateEntity(SupplierAddress a, SupplierAddressUpdateRequest req) {
        a.setLine1(req.getLine1());
        a.setLine2(req.getLine2());
        a.setCity(req.getCity());
        a.setState(req.getState());
        a.setCountry(req.getCountry());
        a.setPostcode(req.getPostcode());
    }
}
