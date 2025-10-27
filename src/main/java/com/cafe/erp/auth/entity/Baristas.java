package com.cafe.erp.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "baristas")
public class Baristas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String username;
    private String full_name;
    private String role;

    private LocalDate hire_date;
    private String password_hash;
    private OffsetDateTime last_login;

    @JsonProperty("is_active")
    private boolean is_active;

    private String phone_number;

    // Custom getter to avoid _active
    @JsonProperty("is_active")
    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
