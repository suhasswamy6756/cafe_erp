package com.cafe.erp.catalogue.model;

import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tax")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_type", length = 100, nullable = false)
    private String taxType;

    @Column(name = "tax_code", length = 50, nullable = false, unique = true)
    private String taxCode;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Example: "Online, Offline" or JSON string
    @Column(name = "applicable_modes", length = 255)
    private String applicableModes;

    // Example: "Product", "Service"
    @Column(name = "applicable_on", length = 100)
    private String applicableOn;

    @Column(name = "tax_percentage", nullable = false)
    private BigDecimal taxPercentage;

    @Column(name = "active", nullable = false)
    private Boolean isActive;

    @Builder.Default
    private Boolean isDeleted = false;
}
