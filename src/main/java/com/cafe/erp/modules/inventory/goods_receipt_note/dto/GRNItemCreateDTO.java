package com.cafe.erp.modules.inventory.goods_receipt_note.dto;


import lombok.Data;

@Data
public class GRNItemCreateDTO {
    private Long poItemId;  // Link to PO item (ordered qty & cost defined there)
}
