package com.cafe.erp.auth.entity;

import java.io.Serializable;

// Defines the composite key
public record UserRolesMappingId(Long userId, Long roleId) implements Serializable {
}
