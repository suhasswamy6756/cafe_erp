package com.cafe.erp.modules.inventory.goods_receipt_note.mapper;


import com.cafe.erp.modules.inventory.goods_receipt_note.dto.*;
import com.cafe.erp.modules.inventory.goods_receipt_note.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GRNMapper {

    public GRNItemDTO toItemDTO(GRNItem entity) {
        if (entity == null) return null;

        return GRNItemDTO.builder()
                .grnItemId(entity.getGrnItemId())
//                .materialId(entity.getMaterialId())
//                .materialName(entity.getMaterial().getName())
                .uomCode(entity.getUomCode())
                .orderedQty(entity.getOrderedQty())
                .deliveredQty(entity.getDeliveredQty())
                .acceptedQty(entity.getAcceptedQty())
                .rejectedQty(entity.getRejectedQty())
//                .batchNo(entity.getBatchNo())
                .expiryDate(entity.getExpiryDate())
                .unitCost(entity.getUnitCost())
                .totalCost(entity.getTotalCost())
                .build();
    }

    public GRNCreateResponseDTO toCreateResponse(GRNHeader header) {

        return GRNCreateResponseDTO.builder()
                .grnId(header.getGrnId())
                .grnNumber(header.getGrnNumber())
                .supplierId(header.getSupplier().getSupplierId())
                .purchaseOrderId(header.getPurchaseOrder().getPoId())
                .locationId(header.getLocationId())
                .status(header.getStatus())
                .createdAt(header.getCreatedAt())
                .items(header.getItems()
                        .stream()
                        .map(this::toItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public GRNResponseDTO toResponse(GRNHeader header) {

        return GRNResponseDTO.builder()
                .grnId(header.getGrnId())
                .grnNumber(header.getGrnNumber())
                .invoiceNumber(header.getInvoiceNumber())
//                .invoiceDate(header.getInvoiceDate())
                .supplierId(header.getSupplier().getSupplierId())
                .purchaseOrderId(header.getPurchaseOrder().getPoId())
                .locationId(header.getLocationId())
                .status(header.getStatus())
                .receivedDate(header.getReceivedDate())
                .items(header.getItems()
                        .stream()
                        .map(this::toItemDTO)
                        .collect(Collectors.toList()))
//                .(header.getRemarks() == null ? null :
//                        header.getRemarks().stream()
//                                .map(this::toRejectionDTO)
//                                .collect(Collectors.toList()))
                .build();
    }

    public GRNRejectionDTO toRejectionDTO(GRNRejectionLog log) {
        if (log == null) return null;

        return GRNRejectionDTO.builder()
                .rejectionId(log.getRejectionId())
                .grnItemId(log.getGrnItem().getGrnItemId())
                .rejectedQty(log.getRejectedQty())
                .reason(log.getReason())
                .actionTaken(log.getActionTaken())
//                .evidenceImage(log.getEvidenceImage())
                .build();
    }
}
