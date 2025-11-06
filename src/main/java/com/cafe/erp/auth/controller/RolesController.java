package com.cafe.erp.auth.controller;

import com.cafe.erp.auth.entity.Roles;
import com.cafe.erp.auth.service.RolesService;
import com.cafe.erp.common.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CHEF')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Roles>> registerRole(@RequestBody Roles role) {
        return ResponseEntity.ok(ApiResponse.success("Role registered successfully", rolesService.registerRole(role), 201));
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Roles>>> getAllRoles() {
        return ResponseEntity.ok(ApiResponse.success("Roles fetched successfully", rolesService.getAllRoles(), 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Roles>> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Role fetched successfully", rolesService.getRoleById(id), 200));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Roles>> updateRole(
            @PathVariable Long roleId,
            @RequestBody Roles updateRequest
    ) {
        Roles updated = rolesService.updateRole(roleId, updateRequest);
        return ResponseEntity.ok(ApiResponse.success("Role updated successfully", updated, 200));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success("Role deleted successfully", null, 200));
    }
}
