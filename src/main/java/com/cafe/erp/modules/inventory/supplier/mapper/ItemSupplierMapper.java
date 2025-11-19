package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.ItemSupplierDTO;
import com.cafe.erp.modules.inventory.supplier.entity.ItemSupplier;
import org.springframework.stereotype.Component;



@Component
public class ItemSupplierMapper {

    public ItemSupplierDTO toDTO(ItemSupplier e) {
        ItemSupplierDTO dto = new ItemSupplierDTO();
        dto.setId(e.getId());
        dto.setItemId(e.getItem().getId());
        dto.setSupplierId(e.getSupplier().getSupplierId());
        dto.setSupplierName(e.getSupplier().getName());
        dto.setItemName(e.getItem().getName());
        return dto;
    }
}

