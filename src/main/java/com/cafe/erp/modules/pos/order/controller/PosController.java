package com.cafe.erp.modules.pos.order.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.pos.order.dto.PosOrderCreateRequest;
import com.cafe.erp.modules.pos.order.dto.PosOrderResponseDTO;
import com.cafe.erp.modules.pos.order.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pos")
@RequiredArgsConstructor
public class PosController {
    private final PosOrderService posOrderService;

    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<PosOrderResponseDTO>> createOrder(@RequestBody PosOrderCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Created order", posOrderService.createOrder(request), 201));
    }


}
