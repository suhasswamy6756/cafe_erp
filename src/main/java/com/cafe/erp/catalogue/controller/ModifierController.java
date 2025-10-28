package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.model.Modifier;
import com.cafe.erp.catalogue.service.ModifierService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/modifiers")
public class ModifierController {

    private final ModifierService modifierService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Modifier>>> getAllModifiers() {
        return ResponseEntity.ok(ApiResponse.success("Modifiers fetched successfully", modifierService.getAllModifiers(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Modifier>> getModifierById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Modifier fetched successfully", modifierService.getModifierById(id), 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Modifier>> createModifier(@RequestBody Modifier modifier) {
        return ResponseEntity.ok(ApiResponse.success("Modifier created successfully", modifierService.createModifier(modifier), 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Modifier>> updateModifier(@PathVariable Long id, @RequestBody Modifier modifier) {
        return ResponseEntity.ok(ApiResponse.success("Modifier updated successfully", modifierService.updateModifier(id, modifier), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteModifier(@PathVariable Long id) {
        modifierService.deleteModifier(id);
        return ResponseEntity.ok(ApiResponse.success("Modifier deleted successfully", null, 200));
    }


}
