package com.cafe.erp.modules.catalogue.modifier_group.controller;

import com.cafe.erp.common.model.ApiResponse;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupRequestDTO;
import com.cafe.erp.modules.catalogue.modifier_group.dto.ModifierGroupResponseDTO;
import com.cafe.erp.modules.catalogue.modifier_group.service.ModifierGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modifier-groups")
@RequiredArgsConstructor
public class ModifierGroupsController {


    private final ModifierGroupService modifierGroupService;

    @PostMapping
    public ResponseEntity<ApiResponse<ModifierGroupResponseDTO>> createModifierGroup(
            @RequestBody ModifierGroupRequestDTO dto) {

        ModifierGroupResponseDTO response = modifierGroupService.createModifierGroup(dto);

        return ResponseEntity.ok(
                ApiResponse.success("Modifier group created successfully", response, 201)
        );
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ModifierGroupResponseDTO>>> getAllModifierGroups() {

        List<ModifierGroupResponseDTO> groups = modifierGroupService.getAllModifierGroups();

        return ResponseEntity.ok(
                ApiResponse.success("Modifier groups fetched successfully", groups, 200)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifierGroupResponseDTO>> getModifierGroupById(
            @PathVariable Long id) {

        ModifierGroupResponseDTO group = modifierGroupService.getModifierGroupById(id);

        return ResponseEntity.ok(
                ApiResponse.success("Modifier group fetched successfully", group, 200)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ModifierGroupResponseDTO>> updateModifierGroup(
            @PathVariable Long id,
            @RequestBody ModifierGroupRequestDTO dto) {

        ModifierGroupResponseDTO updated = modifierGroupService.updateModifierGroup(id, dto);

        return ResponseEntity.ok(
                ApiResponse.success("Modifier group updated successfully", updated, 200)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteModifierGroup(@PathVariable Long id) {

        modifierGroupService.deleteModifierGroup(id);

        return ResponseEntity.ok(
                ApiResponse.success("Modifier group deleted successfully", null, 200)
        );
    }
}
