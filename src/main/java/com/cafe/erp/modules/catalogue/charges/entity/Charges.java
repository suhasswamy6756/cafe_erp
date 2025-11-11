package com.cafe.erp.modules.catalogue.charges.entity;


import com.cafe.erp.common.enums.ChargeType;
import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "charges")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Charges extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private Long chargeId;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @Column(name = "handle",nullable = false, unique = true)
    private String handle;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChargeType type;

    @Column(name = "value")
    private Double value;

    @Column(name ="is_taxable")
    private Boolean isTaxable = false;

    @Column(name = "is_active")
    private Boolean isActive = true;
}

