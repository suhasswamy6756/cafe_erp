package com.cafe.erp.modules.authorization.auth.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private BaristaResponseDTO barista;
}
