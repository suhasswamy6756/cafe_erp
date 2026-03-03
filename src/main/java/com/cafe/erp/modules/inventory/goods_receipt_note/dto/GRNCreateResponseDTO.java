package com.cafe.erp.modules.inventory.goods_receipt_note.dto;

import com.cafe.erp.common.enums.GRNStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class GRNCreateResponseDTO {

    private Long grnId;
    private String grnNumber;

    private Long supplierId;
    private Long purchaseOrderId;
    private Long locationId;

    private GRNStatus status;
    private LocalDateTime createdAt;

    private List<GRNItemDTO> items;
}

