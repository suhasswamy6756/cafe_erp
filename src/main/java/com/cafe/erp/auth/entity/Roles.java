package com.cafe.erp.auth.entity;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Roles extends BaseEntity {
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Column(name = "description", nullable = false)
    private String roleDescription;

    @Column(name = "is_active")
    private boolean isActive;

}
