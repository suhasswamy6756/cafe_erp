package com.cafe.erp.auth.controller;

import com.cafe.erp.auth.entity.UserRolesMapping;
import com.cafe.erp.auth.service.UserRoleMappingService;
import com.cafe.erp.common.model.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@RequiredArgsConstructor
public class UserRoleMappingController {

    private final UserRoleMappingService service;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<UserRolesMapping>> assignRole(
            @RequestParam Long userId,
            @RequestParam Long roleId,
            @RequestParam Long assignedBy) {
        return ResponseEntity.ok(ApiResponse.success("Role assigned successfully", service.assignRole(userId, roleId, assignedBy), 200));
    }

    @PostMapping("/revoke")
    public ResponseEntity<ApiResponse<String>> revokeRole(
            @RequestParam Long userId,
            @RequestParam Long roleId,
            @RequestParam Long revokedBy) {
        service.revokeRole(userId, roleId, revokedBy);
        return ResponseEntity.ok(ApiResponse.success("Role revoked successfully", null, 200));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<UserRolesMapping>>> getRolesForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success("Roles fetched successfully", service.getRolesForUser(userId), 200));
    }
}
