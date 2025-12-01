package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.MaterialSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.entity.MaterialSupplier;
import org.springframework.stereotype.Component;



@Component
public class ItemSupplierMapper {

    public MaterialSupplierDTO toDTO(MaterialSupplier e) {
        MaterialSupplierDTO dto = new MaterialSupplierDTO();
        dto.setId(e.getId());
        dto.setItemId(e.getMaterial().getMaterialId());
        dto.setSupplierId(e.getSupplier().getSupplierId());
        dto.setSupplierName(e.getSupplier().getName());
        dto.setItemName(e.getMaterial().getName());
//        dto.setUnitCost(e.getUnitCost());
//        dto.setLeadTimeDays(e.getLeadTimeDays());
//        dto.setIsPrimary(e.getIsPrimary());
        return dto;
    }
}

