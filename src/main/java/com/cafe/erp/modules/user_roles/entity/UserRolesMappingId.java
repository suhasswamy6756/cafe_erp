package com.cafe.erp.modules.user_roles.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRolesMappingId implements Serializable {
    private Long userId;
    private Long roleId;
}
