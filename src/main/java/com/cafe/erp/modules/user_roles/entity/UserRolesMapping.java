package com.cafe.erp.modules.user_roles.entity;

import com.cafe.erp.modules.auth.entity.Baristas;
import com.cafe.erp.modules.roles.entity.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "cafe_users_roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@IdClass(UserRolesMappingId.class)
public class UserRolesMapping {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private Baristas barista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    @JsonIgnore // Hide the full role object in JSON
    private Roles role;

    // ✅ Add this field for frontend simplicity
    @Transient
    private String roleName;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @Column(name = "assigned_at")
    private OffsetDateTime assignedAt = OffsetDateTime.now();

    @Column(name = "revoked_by")
    private Long revokedBy;

    @Column(name = "revoked_at")
    private OffsetDateTime revokedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    // ✅ Automatically return roleName if available
    public String getRoleName() {
        if (role != null && role.getRoleName() != null) {
            return role.getRoleName();
        }
        return roleName; // fallback if lazy or detached
    }

    // Optional convenience: set it manually from service
    public void setRoleNameFromEntity() {
        if (role != null) {
            this.roleName = role.getRoleName();
        }
    }
}
