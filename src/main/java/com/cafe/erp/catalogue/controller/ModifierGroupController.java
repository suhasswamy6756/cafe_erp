package com.cafe.erp.catalogue.controller;

import com.cafe.erp.catalogue.model.ModifierGroup;
import com.cafe.erp.catalogue.service.ModifierGroupService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modifier-groups")
@RequiredArgsConstructor
public class ModifierGroupController {

    private final ModifierGroupService modifierGroupService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModifierGroup>>> getAllModifierGroups() {
        return ResponseEntity.ok(ApiResponse.success("ModifierGroups fetched successfully", modifierGroupService.getAllModifierGroups(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifierGroup>> getModifierGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("ModifierGroup fetched successfully", modifierGroupService.getModifierGroupById(id), 200));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ModifierGroup>> createModifierGroup(@RequestBody ModifierGroup modifierGroup) {
    return ResponseEntity.ok(ApiResponse.success("ModifierGroup created successfully", modifierGroupService.createModifierGroup(modifierGroup), 201));    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifierGroup>> updateModifierGroup(@PathVariable Long id, @RequestBody ModifierGroup modifierGroup) {
        return ResponseEntity.ok(ApiResponse.success("ModifierGroup updated successfully", modifierGroupService.updateModifierGroup(id, modifierGroup), 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteModifierGroup(@PathVariable Long id) {
        modifierGroupService.deleteModifierGroup(id);
        return ResponseEntity.ok(ApiResponse.success("deleted modifier Group succesfully", null, 200));
    }

}
