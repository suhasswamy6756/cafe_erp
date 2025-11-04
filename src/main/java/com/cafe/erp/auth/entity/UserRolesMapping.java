package com.cafe.erp.auth.entity;

import jakarta.persistence.Column;

import java.time.OffsetDateTime;

public class UserRolesMapping {
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "assigned_by", nullable = false)
    private Long assignedBy;

    @Column(name = "assigned_at", nullable = false)
    private OffsetDateTime assignedAt;

    @Column(name = "revoked_by", nullable = false)
    private Long revokedBy;

    @Column(name = "revoked_at")
    private OffsetDateTime revokedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;


}
