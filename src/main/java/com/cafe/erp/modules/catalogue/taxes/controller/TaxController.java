package com.cafe.erp.modules.catalogue.taxes.controller;


import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.taxes.dto.TaxRequestDTO;
import com.cafe.erp.modules.catalogue.taxes.dto.TaxResponseDTO;
import com.cafe.erp.modules.catalogue.taxes.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    // ✅ CREATE TAX
    @PostMapping
    public ResponseEntity<ApiResponse<TaxResponseDTO>> createTax(
            @Valid @RequestBody TaxRequestDTO request) {

        TaxResponseDTO response = taxService.createTax(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tax created successfully", response, 201));
    }

    // ✅ UPDATE TAX
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaxResponseDTO>> updateTax(
            @PathVariable Long id,
            @Valid @RequestBody TaxRequestDTO request) {

        TaxResponseDTO response = taxService.updateTax(id, request);

        return ResponseEntity.ok(ApiResponse.success("Tax updated successfully", response, 200));
    }

    // ✅ GET ALL TAXES
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaxResponseDTO>>> getAllTaxes() {
        List<TaxResponseDTO> list = taxService.getAllTaxes();
        return ResponseEntity.ok(ApiResponse.success("Taxes fetched", list, 200));
    }

    // ✅ GET TAX BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaxResponseDTO>> getTaxById(@PathVariable Long id) {
        TaxResponseDTO response = taxService.getTaxById(id);
        return ResponseEntity.ok(ApiResponse.success("Tax fetched", response, 200));
    }

    // ✅ DELETE TAX (SOFT DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.ok(ApiResponse.success("Tax deleted successfully", null, 200));
    }
}

