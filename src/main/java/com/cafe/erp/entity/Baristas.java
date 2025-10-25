package com.cafe.erp.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "baristas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Baristas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    private String username;
    private String full_name;
    private String role;

    private LocalDate hire_date; // DATE type
    private String password_hash;
    private OffsetDateTime last_login; // TIMESTAMP WITH TIME ZONE

    private boolean is_active;
    private String phone_number;

    public Baristas(String full_name, LocalDate hire_date, boolean is_active, OffsetDateTime last_login,
                    String password_hash, String phone_number, String role, int user_id, String username) {
        this.full_name = full_name;
        this.hire_date = hire_date;
        this.is_active = is_active;
        this.last_login = last_login;
        this.password_hash = password_hash;
        this.phone_number = phone_number;
        this.role = role;
        this.user_id = user_id;
        this.username = username;
    }
}
