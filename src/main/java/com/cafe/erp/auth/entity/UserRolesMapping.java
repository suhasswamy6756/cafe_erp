package com.cafe.erp.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRolesMappingId.class)
@Builder
public class UserRolesMapping {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "assigned_by")
    private Long assignedBy;

    @Column(name = "assigned_at")
    private OffsetDateTime assignedAt = OffsetDateTime.now();

    @Column(name = "revoked_by")
    private Long revokedBy;

    @Column(name = "revoked_at")
    private OffsetDateTime revokedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;
}
