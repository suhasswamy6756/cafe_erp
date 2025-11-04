package com.cafe.erp.auth.DTO;

import com.cafe.erp.auth.entity.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaristaResponseDTO {
    private Long userId;
    private String username;
    private String fullName;
    private Set<RoleResponseDTO> roles;
}
