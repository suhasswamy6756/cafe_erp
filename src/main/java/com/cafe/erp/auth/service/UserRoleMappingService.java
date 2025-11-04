package com.cafe.erp.auth.service;

import com.cafe.erp.auth.entity.UserRolesMapping;
import com.cafe.erp.auth.repository.UserRolesMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleMappingService {

    private final UserRolesMappingRepository repository;

    public UserRolesMapping assignRole(Long userId, Long roleId, Long assignedBy) {
        UserRolesMapping mapping = UserRolesMapping.builder()
                .userId(userId)
                .roleId(roleId)
                .assignedBy(assignedBy)
                .assignedAt(OffsetDateTime.now())
                .isDeleted(false)
                .build();
        return repository.save(mapping);
    }

    public void revokeRole(Long userId, Long roleId, Long revokedBy) {
        repository.findById(new com.cafe.erp.auth.entity.UserRolesMappingId(userId, roleId))
                .ifPresent(mapping -> {
                    mapping.setRevokedBy(revokedBy);
                    mapping.setRevokedAt(OffsetDateTime.now());
                    repository.save(mapping);
                });
    }

    public List<UserRolesMapping> getRolesForUser(Long userId) {
        return repository.findByUserIdAndIsDeletedFalse(userId);
    }
}
