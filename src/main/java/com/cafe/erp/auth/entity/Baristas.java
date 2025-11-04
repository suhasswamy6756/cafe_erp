package com.cafe.erp.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "cafe_users")
public class Baristas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;


    // ✅ relationship to mapping table (no @Where)
    @OneToMany(mappedBy = "barista", fetch = FetchType.LAZY)
    private Set<UserRolesMapping> roleMappings = new HashSet<>();

    // ✅ transient field for returning active roles in API
    @Transient
    private Set<Roles> activeRoles = new HashSet<>();


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

    public Set<Roles> getActiveRoles() {
        if (roleMappings == null) return Set.of();

        return roleMappings.stream()
                .filter(mapping -> !mapping.isDeleted() && mapping.getRevokedAt() == null)
                .map(UserRolesMapping::getRole)
                .filter(Roles::isActive)
                .collect(java.util.stream.Collectors.toSet());
    }

}
