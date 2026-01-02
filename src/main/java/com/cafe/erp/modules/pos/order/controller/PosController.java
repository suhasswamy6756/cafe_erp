package com.cafe.erp.modules.pos.order.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.pos.order.dto.PosCheckoutRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderCreateRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderItemRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderResponseDTO;
import com.cafe.erp.modules.pos.order.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pos")
@RequiredArgsConstructor
public class PosController {
    private final PosOrderService posOrderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> createOrder(@RequestBody PosOrderCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Created order", posOrderService.createOrder(request), 201));
    }

    @PostMapping("/orders/{orderId}/items")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> addItem(@PathVariable Long orderId, @RequestBody PosOrderItemRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Item added to order", posOrderService.addItem(orderId,request), 201));
    }

    @DeleteMapping("/orders/{orderId}/items/{itemId}")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> removeItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        return ResponseEntity.ok(ApiResponse.success("Item removed", posOrderService.removeItem(orderId, itemId), 200));
    }

    @PostMapping("/orders/{orderId}/checkout")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> checkout(@PathVariable Long orderId, @RequestBody PosCheckoutRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Payment successful", posOrderService.checkout(request), 200));
    }

    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> cancel(@PathVariable Long orderId) {
        posOrderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("Order cancelled", posOrderService.getOrder(orderId), 200));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order fetched", posOrderService.getOrder(orderId), 200));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<PosOrderResponseDTO>>> listOrders() {
        return ResponseEntity.ok(ApiResponse.success("Orders fetched", posOrderService.listOrders(), 200));
    }


}
