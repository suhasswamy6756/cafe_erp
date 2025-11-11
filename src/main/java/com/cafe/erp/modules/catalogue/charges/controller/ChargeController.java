package com.cafe.erp.modules.catalogue.charges.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.charges.dto.ChargeRequestDTO;
import com.cafe.erp.modules.catalogue.charges.dto.ChargeResponseDTO;
import com.cafe.erp.modules.catalogue.charges.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeController {

    private final ChargeService chargeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ChargeResponseDTO>> createCharge(@RequestBody ChargeRequestDTO dto) {
        return ResponseEntity.ok(ApiResponse.success("Charge created successfully", chargeService.createCharge(dto), 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ChargeResponseDTO>> updateCharge(@RequestBody ChargeRequestDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Charge updated successfully", chargeService.updateCharge(id, dto), 200));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChargeResponseDTO>>> getAllCharges() {
        return ResponseEntity.ok(ApiResponse.success("All charges fetched successfully", chargeService.getAllCharges(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChargeResponseDTO>> getChargeById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Charge fetched successfully", chargeService.getChargeById(id), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCharge(@PathVariable Long id) {
        chargeService.deleteCharge(id);
        return ResponseEntity.ok(ApiResponse.success("Charge deleted successfully", null, 200));
    }
}

