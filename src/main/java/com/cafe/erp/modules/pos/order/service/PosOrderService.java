package com.cafe.erp.modules.pos.order.service;

import com.cafe.erp.modules.pos.order.dto.PosOrderCreateRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderResponseDTO;

import java.util.List;

public interface PosOrderService {

    PosOrderResponseDTO createOrder(PosOrderCreateRequest request);

    PosOrderResponseDTO getOrder(Long orderId);

    List<PosOrderResponseDTO> listOrders();

    void cancelOrder(Long orderId);
}

