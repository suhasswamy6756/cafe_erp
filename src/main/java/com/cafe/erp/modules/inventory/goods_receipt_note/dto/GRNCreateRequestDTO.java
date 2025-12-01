package com.cafe.erp.modules.inventory.goods_receipt_note.dto;

import lombok.Data;

import java.util.List;


@Data
public class GRNCreateRequestDTO {
    private Long purchaseOrderId;
    private Long supplierId;
    private Long locationId;

//    private List<GRNItemCreateDTO> items;
}


