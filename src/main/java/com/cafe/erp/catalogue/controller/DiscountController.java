package com.cafe.erp.catalogue.controller;


import com.cafe.erp.catalogue.model.Discount;
import com.cafe.erp.catalogue.service.DiscountService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<ApiResponse<Discount>> createDiscount(@RequestBody Discount discount) {
        return ResponseEntity.ok(ApiResponse.success("Discount created successfully", discountService.createDiscount(discount), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Discount>>> getAllDiscounts() {
        return ResponseEntity.ok(ApiResponse.success("Discounts fetched successfully", discountService.getAllDiscounts(), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Discount>> updateDiscount(@PathVariable Long id, @RequestBody Discount discount) {
        return ResponseEntity.ok(ApiResponse.success("Discount updated successfully", discountService.updateDiscount(id, discount), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Discount>> deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.ok(ApiResponse.success("Discount deleted successfully", null, 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Discount>> getDiscountById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Discount fetched successfully", discountService.getDiscountById(id), 200));
    }
}
