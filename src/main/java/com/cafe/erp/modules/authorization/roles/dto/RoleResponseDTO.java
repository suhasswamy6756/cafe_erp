package com.cafe.erp.modules.authorization.roles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {
    private Long roleId;
    private String roleName;
    private String roleDescription;
    private boolean active;
}
