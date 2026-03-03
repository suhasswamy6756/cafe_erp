package com.cafe.erp.modules.authorization.user_roles.repository;

import com.cafe.erp.modules.authorization.user_roles.entity.UserRolesMapping;
import com.cafe.erp.modules.authorization.user_roles.entity.UserRolesMappingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesMappingRepository extends JpaRepository<UserRolesMapping, UserRolesMappingId> {

    List<UserRolesMapping> findByUserIdAndIsDeletedFalseAndRevokedAtIsNull(Long userId);

    boolean existsByRoleId(Long roleId);

    long countByRoleId(Long roleId);
    boolean existsByUserIdAndRoleIdAndIsDeletedFalse(Long userId, Long roleId);
}
