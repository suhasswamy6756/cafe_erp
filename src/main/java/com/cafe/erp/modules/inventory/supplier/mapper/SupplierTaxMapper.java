package com.cafe.erp.modules.inventory.supplier.mapper;


import com.cafe.erp.modules.catalogue.taxes.entity.Tax;
import com.cafe.erp.modules.inventory.supplier.dto.SupplierTaxDTO;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import com.cafe.erp.modules.inventory.supplier.entity.SupplierMultipleTax;
import org.springframework.stereotype.Component;

@Component
public class SupplierTaxMapper {

    public SupplierMultipleTax toEntity(Supplier supplier, Tax tax) {
        SupplierMultipleTax e = new SupplierMultipleTax();
        e.setSupplier(supplier);
        e.setTax(tax);
        return e;
    }

    public void updateEntity(SupplierMultipleTax e, Tax tax) {
        e.setTax(tax);
    }

    public SupplierTaxDTO toDTO(SupplierMultipleTax e) {
        SupplierTaxDTO dto = new SupplierTaxDTO();
        dto.setId(e.getId());
        dto.setSupplierId(e.getSupplier().getSupplierId());
        dto.setTaxId(e.getTax().getTaxId());
        dto.setTaxName(e.getTax().getName());
        dto.setTaxDescription(e.getTax().getDescription());
        dto.setTaxValue(e.getTax().getValue());
        return dto;
    }
}

