package com.cafe.erp.modules.inventory.goods_receipt_note.dto;

import com.cafe.erp.common.enums.GRNStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GRNResponseDTO {

    private Long grnId;
    private String grnNumber;
    private String invoiceNumber;

    private Long locationId;
    private Long supplierId;
    private Long purchaseOrderId;

    private GRNStatus status;
    private LocalDateTime receivedDate;

    private List<GRNItemDTO> items;
}

