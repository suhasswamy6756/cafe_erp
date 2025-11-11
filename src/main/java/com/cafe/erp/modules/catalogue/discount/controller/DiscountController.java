package com.cafe.erp.modules.catalogue.discount.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.discount.dto.DiscountRequestDTO;
import com.cafe.erp.modules.catalogue.discount.dto.DiscountResponseDTO;
import com.cafe.erp.modules.catalogue.discount.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> create(@RequestBody DiscountRequestDTO dto) {
        return ResponseEntity.ok(
                ApiResponse.success("Discount created successfully", discountService.createDiscount(dto), 201)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> update(
            @PathVariable Long id,
            @RequestBody DiscountRequestDTO dto
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Discount updated successfully", discountService.updateDiscount(id, dto), 200)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DiscountResponseDTO>>> getAll() {
        return ResponseEntity.ok(
                ApiResponse.success("All discounts fetched", discountService.getAllDiscounts(), 200)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountResponseDTO>> get(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Discount fetched", discountService.getDiscountById(id), 200)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok(
                ApiResponse.success("Discount deleted", null, 200)
        );
    }
}

