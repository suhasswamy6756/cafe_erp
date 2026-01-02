package com.cafe.erp.modules.inventory.goods_receipt_note.dto;


import lombok.Data;
import java.util.List;

@Data
public class GRNSubmitRequestDTO {
    private Long grnId;
    private List<GRNItemSubmitDTO> items;
}

