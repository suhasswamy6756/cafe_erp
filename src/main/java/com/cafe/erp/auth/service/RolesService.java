package com.cafe.erp.auth.service;

import com.cafe.erp.auth.entity.Roles;
import com.cafe.erp.auth.repository.RolesRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    public Roles registerRole(Roles role) {
        return rolesRepository.save(role);
    }

    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    public Roles getRoleById(Long id) {
        return rolesRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public Roles updateRole(Roles role) {
        return rolesRepository.save(role);
    }

    public void deleteRole(Long id) {
        rolesRepository.deleteById(id);
    }
}
