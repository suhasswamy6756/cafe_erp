package com.cafe.erp.auth.DTO;

import com.cafe.erp.auth.entity.Baristas;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Baristas barista;
}
