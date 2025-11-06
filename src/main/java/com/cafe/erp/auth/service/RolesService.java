package com.cafe.erp.auth.service;

import com.cafe.erp.auth.entity.Roles;
import com.cafe.erp.auth.repository.RolesRepository;
import com.cafe.erp.auth.repository.UserRolesMappingRepository;
import com.cafe.erp.common.exception.AlreadyExistsException;
import com.cafe.erp.common.exception.BadRequestException;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesService {

    private final RolesRepository rolesRepository;
    private final UserRolesMappingRepository userRolesMappingRepository;

    /**
     * Register a new role.
     */
    public Roles registerRole(Roles role) {

        // Check duplicate role name
        if (rolesRepository.existsByRoleNameAndIsDeletedFalse(role.getRoleName())) {
            throw new AlreadyExistsException("Role already exists: " + role.getRoleName());
        }

        role.setCreatedBy(currentUser());
        role.setCreatedAt(LocalDateTime.now());

        return rolesRepository.save(role);
    }

    /**
     * Get all active (non-deleted) roles.
     */
    public List<Roles> getAllRoles() {
        return rolesRepository.findAllByIsDeletedFalse();
    }

    /**
     * Get a role by ID (non-deleted).
     */
    public Roles getRoleById(Long id) {
        return rolesRepository.findByRoleIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
    }

    /**
     * Update a role.
     */
    @Transactional
    public Roles updateRole(Long roleId, Roles updateRequest) {

        // Fetch role, including soft delete protection
        Roles existingRole = getRoleById(roleId);

        // Check for duplicate role name (if updated)
        if (!existingRole.getRoleName().equals(updateRequest.getRoleName()) &&
                rolesRepository.existsByRoleNameAndIsDeletedFalse(updateRequest.getRoleName())) {

            throw new AlreadyExistsException(
                    "Role name already exists: " + updateRequest.getRoleName());
        }

        // Update allowed fields
        existingRole.setRoleName(updateRequest.getRoleName());
        existingRole.setRoleDescription(updateRequest.getRoleDescription());
        existingRole.setUpdatedAt(LocalDateTime.now());
        existingRole.setUpdatedBy(currentUser());

        return rolesRepository.save(existingRole);
    }


    /**
     * Soft delete a role.
     */
    @Transactional
    public void deleteRole(Long id) {

        Roles role = getRoleById(id);

        // Prevent deleting assigned roles
        if (userRolesMappingRepository.existsByRoleId(id)) {
            throw new BadRequestException(
                    "Cannot delete role: It is assigned to existing users. Remove assignments before deleting.");
        }

        role.setIsDeleted(true);
        role.setDeletedAt(LocalDateTime.now());
        role.setDeletedBy(currentUser());

        rolesRepository.save(role);
    }

    /**
     * Get current authenticated username.
     */
    private String currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : "system";
    }
}
