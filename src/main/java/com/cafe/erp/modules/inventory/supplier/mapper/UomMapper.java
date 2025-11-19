package com.cafe.erp.modules.inventory.supplier.mapper;

import com.cafe.erp.modules.inventory.supplier.dto.UomCreateRequest;
import com.cafe.erp.modules.inventory.supplier.dto.UomDTO;
import com.cafe.erp.modules.inventory.supplier.dto.UomUpdateRequest;
import com.cafe.erp.modules.inventory.supplier.entity.Uom;
import org.springframework.stereotype.Component;

@Component
public class UomMapper {

    public UomDTO toDTO(Uom entity) {
        if (entity == null) return null;

        return new UomDTO(
                entity.getUomCode(),
                entity.getDescription(),
                entity.getBaseUnit(),
                entity.getConversionFactor(),
                entity.getIsActive()
        );
    }

    public Uom toEntity(UomCreateRequest req) {
        Uom e = new Uom();
        e.setUomCode(req.getUomCode());
        e.setDescription(req.getDescription());
        e.setBaseUnit(req.getBaseUnit());
        e.setConversionFactor(req.getConversionFactor());
        return e;
    }

    public void updateEntity(Uom e, UomUpdateRequest req) {
        e.setDescription(req.getDescription());
        e.setBaseUnit(req.getBaseUnit());
        e.setConversionFactor(req.getConversionFactor());
    }
}

