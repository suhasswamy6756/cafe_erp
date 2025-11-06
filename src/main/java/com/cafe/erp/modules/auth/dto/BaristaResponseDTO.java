package com.cafe.erp.modules.auth.dto;

import com.cafe.erp.modules.roles.dto.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaristaResponseDTO  {
    private Long userId;
    private String username;
    private String fullName;
    private OffsetDateTime lastLogin;
    private String phoneNumber;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<RoleResponseDTO> roles;



}
