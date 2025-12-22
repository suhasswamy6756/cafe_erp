package com.cafe.erp.modules.pos.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class PosOrderCreateRequest {
    private Long locationId;
    private String paymentMode;
    private String remarks;
    private List<PosOrderItemRequest> items;
}
