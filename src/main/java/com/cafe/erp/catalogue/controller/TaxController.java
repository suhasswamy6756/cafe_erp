package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.model.Tax;
import com.cafe.erp.catalogue.service.TaxService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tax")
public class TaxController {

    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<ApiResponse<Tax>> createTax(@RequestBody Tax tax) {
        return ResponseEntity.ok(ApiResponse.success("Tax created successfully", taxService.createTax(tax), 201));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tax>>> getAllTaxes() {
        return ResponseEntity.ok(ApiResponse.success("Taxes fetched successfully", taxService.getAllTaxes(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Tax>> getTaxById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Tax fetched successfully", taxService.getTaxById(id), 200));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Tax>> updateTax(@PathVariable Long id, @RequestBody Tax tax) {
        return ResponseEntity.ok(ApiResponse.success("Tax updated successfully", taxService.updateTax(id, tax), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Tax>> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.ok(ApiResponse.success("Tax deleted successfully", null, 200));
    }
}
