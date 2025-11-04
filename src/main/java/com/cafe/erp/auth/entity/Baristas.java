package com.cafe.erp.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "baristas")
public class Baristas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();


    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @JsonProperty("is_active")
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
}
