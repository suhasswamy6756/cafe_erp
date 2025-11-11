package com.cafe.erp.modules.catalogue.taxes.entity;

import com.cafe.erp.common.enums.TaxType;
import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "taxes")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Tax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private Long taxId;

    @Column(name = "name")
    private String name;

    @Column(name = "handle")
    private String handle;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaxType type;// PERCENTAGE, FIXED

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "is_inclusive")
    private Boolean isInclusive;

    @Column(name = "is_active")
    private Boolean isActive;
}
