package com.cafe.erp.auth.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
