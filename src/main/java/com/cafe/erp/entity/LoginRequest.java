package com.cafe.erp.entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
