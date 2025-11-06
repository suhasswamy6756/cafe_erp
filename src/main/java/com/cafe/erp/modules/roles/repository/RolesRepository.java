package com.cafe.erp.modules.roles.repository;

import com.cafe.erp.modules.roles.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    // Fetch single role ONLY if not deleted
    Optional<Roles> findByRoleIdAndIsDeletedFalse(Long roleId);

    // Fetch all non-deleted roles
    List<Roles> findAllByIsDeletedFalse();

    // Check duplicate role name
    boolean existsByRoleNameAndIsDeletedFalse(String roleName);
}
