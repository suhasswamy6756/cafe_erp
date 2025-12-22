package com.cafe.erp.modules.pos.order.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PosOrderResponseDTO {
    private Long orderId;
    private String invoiceNumber;
    private Long locationId;
    private BigDecimal totalAmount;
    private BigDecimal totalTax;
    private String paymentMode;

    private List<PosOrderItemDTO> items;
}

