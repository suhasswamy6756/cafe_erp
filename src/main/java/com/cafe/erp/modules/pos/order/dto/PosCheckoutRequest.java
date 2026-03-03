package com.cafe.erp.modules.pos.order.dto;

import lombok.Data;

@Data
public class PosCheckoutRequest {
    private Long orderId;
    private String paymentMode;   // CASH, CARD, UPI
}

