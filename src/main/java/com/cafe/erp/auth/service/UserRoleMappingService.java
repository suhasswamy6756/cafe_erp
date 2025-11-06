package com.cafe.erp.auth.service;

import com.cafe.erp.auth.entity.Roles;
import com.cafe.erp.auth.entity.UserRolesMapping;
import com.cafe.erp.auth.entity.UserRolesMappingId;
import com.cafe.erp.auth.repository.BaristaRepository;
import com.cafe.erp.auth.repository.RolesRepository;
import com.cafe.erp.auth.repository.UserRolesMappingRepository;
import com.cafe.erp.common.exception.AlreadyExistsException;
import com.cafe.erp.common.exception.BadRequestException;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleMappingService {

    private final UserRolesMappingRepository mappingRepository;
    private final RolesRepository rolesRepository;
    private final BaristaRepository userRepository;

    /**
     * Assign role to a user.
     */
    @Transactional
    public UserRolesMapping assignRole(Long userId, Long roleId) {

        // ✅ 1. Validate user exists
        userRepository.findByUserIdAndIsDeletedFalse(userId);

        // ✅ 2. Validate role exists
        rolesRepository.findByRoleIdAndIsDeletedFalse(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));

        // ✅ 3. Prevent duplicate assignment
        if (mappingRepository.existsByUserIdAndRoleIdAndIsDeletedFalse(userId, roleId)) {
            throw new AlreadyExistsException("User already has this role assigned.");
        }

        UserRolesMapping mapping = UserRolesMapping.builder()
                .userId(userId)
                .roleId(roleId)
                .assignedAt(OffsetDateTime.now())
                .assignedBy(currentUserId())
                .isDeleted(false)
                .build();

        return mappingRepository.save(mapping);
    }

    /**
     * Revoke a user's role (soft delete).
     */
    @Transactional
    public void revokeRole(Long userId, Long roleId) {

        UserRolesMapping mapping = mappingRepository
                .findById(new UserRolesMappingId(userId, roleId))
                .orElseThrow(() -> new ResourceNotFoundException("Role assignment not found for user."));

        // ✅ Prevent double revoke
        if (mapping.getRevokedAt() != null) {
            throw new BadRequestException("Role already revoked for this user.");
        }

        mapping.setRevokedAt(OffsetDateTime.now());
        mapping.setRevokedBy(currentUserId());
        mapping.setDeleted(true);

        mappingRepository.save(mapping);
    }

    /**
     * Get all active roles assigned to a user.
     */
    public List<UserRolesMapping> getRolesForUser(Long userId) {

        // ✅ Validate user
        userRepository.findByUserIdAndIsDeletedFalse(userId);


        return mappingRepository.findByUserIdAndIsDeletedFalseAndRevokedAtIsNull(userId);
    }

    /**
     * Get current authenticated user ID.
     */
    private Long currentUserId() {
        try {
            return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (Exception e) {
            return 0L; // system actions
        }
    }
}
