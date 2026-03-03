package com.cafe.erp.modules.inventory.goods_receipt_note.dto;

import com.cafe.erp.common.enums.GRNAction;
import com.cafe.erp.common.enums.GRNRejectionReason;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GRNRejectionDTO {
    private Long rejectionId;
    private Long grnItemId;
    private BigDecimal rejectedQty;
    private GRNRejectionReason reason;
    private GRNAction actionTaken;
    private String evidenceImage;
}

