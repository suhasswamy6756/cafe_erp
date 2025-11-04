package com.cafe.erp.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "cafe_user_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BaristaToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    @Column(nullable = false, unique = true)
    private String token;

    private boolean revoked;
    private boolean expired;

    private String token_type;

    @ManyToOne
    @JoinColumn(name = "barista_id", nullable = false)
    private Baristas barista;

    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;
    private String created_by;
    private String revoked_by;
    private OffsetDateTime revoked_at;
}
