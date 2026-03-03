package com.cafe.erp.modules.inventory.purchase.mapper;


import com.cafe.erp.modules.inventory.purchase.dto.*;
import com.cafe.erp.modules.inventory.purchase.entity.*;
import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PurchaseOrderMapper {

    public PurchaseOrder toEntity(PurchaseOrderCreateRequest req, Supplier supplier) {
        PurchaseOrder po = new PurchaseOrder();
        po.setSupplier(supplier);
        po.setStatus(com.cafe.erp.common.enums.PurchaseStatus.DRAFT);
        po.setOrderDate(java.time.LocalDate.now());
        po.setExpectedDelivery(req.getExpectedDelivery());
        po.setNotes(req.getNotes());
        return po;
    }

    public PurchaseOrderItem toEntity(PurchaseOrderItemRequest req, PurchaseOrder po, Material material) {
        return PurchaseOrderItem.builder()
                .purchaseOrder(po)
                .material(material)
                .uomCode(req.getUomCode())
                .orderedQty(req.getOrderedQty())
                .unitPrice(req.getUnitPrice())
                .taxPercent(req.getTaxPercent())
                .build();
    }

    public PurchaseOrderDTO toDTO(PurchaseOrder po) {
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setPoId(po.getPoId());
        dto.setSupplierId(po.getSupplier().getSupplierId());
        dto.setSupplierName(po.getSupplier().getName());
        dto.setOrderDate(po.getOrderDate());
        dto.setExpectedDelivery(po.getExpectedDelivery());
        dto.setNotes(po.getNotes());
        dto.setStatus(po.getStatus());

        dto.setItems(po.getItems().stream().map(i -> {
            PurchaseOrderItemDTO itemDTO = new PurchaseOrderItemDTO();
            itemDTO.setPoiId(i.getPoiId());
            itemDTO.setMaterialId(i.getMaterial().getMaterialId());
            itemDTO.setMaterialName(i.getMaterial().getName());
            itemDTO.setUomCode(i.getUomCode());
            itemDTO.setOrderedQty(i.getOrderedQty());
            itemDTO.setUnitPrice(i.getUnitPrice());
            itemDTO.setTaxPercent(i.getTaxPercent());
            return itemDTO;
        }).collect(Collectors.toList()));

        return dto;
    }
}

