package com.cafe.erp.modules.pos.order.service;

import com.cafe.erp.modules.pos.order.dto.PosCheckoutRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderCreateRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderItemRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderResponseDTO;

import java.util.List;

public interface PosOrderService {

    PosOrderResponseDTO createOrder(PosOrderCreateRequest request);

    PosOrderResponseDTO getOrder(Long orderId);

    List<PosOrderResponseDTO> listOrders();

    void cancelOrder(Long orderId);

    PosOrderResponseDTO addItem(Long orderId, PosOrderItemRequest request);

    PosOrderResponseDTO removeItem(Long orderId, Long orderItemId);

    PosOrderResponseDTO checkout(PosCheckoutRequest request);

}

