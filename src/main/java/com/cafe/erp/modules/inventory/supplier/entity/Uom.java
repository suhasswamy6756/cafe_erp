package com.cafe.erp.modules.inventory.supplier.entity;


import com.cafe.erp.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "uoms")
@Getter
@Setter
public class Uom extends BaseEntity {

    @Id
    @Column(name = "uom_code")
    private String uomCode;

    private String description;
    private String baseUnit;

    @Column(name = "conversion_factor")
    private Double conversionFactor;

    @Column(name = "is_active")
    private Boolean isActive = true;
}

